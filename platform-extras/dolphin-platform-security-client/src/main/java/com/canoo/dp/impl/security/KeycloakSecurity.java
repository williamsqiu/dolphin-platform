/*
 * Copyright 2015-2018 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dp.impl.security;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.http.HttpClientConnection;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.security.Security;
import com.canoo.platform.core.DolphinRuntimeException;
import com.canoo.platform.core.http.RequestMethod;
import com.google.gson.Gson;
import org.apiguardian.api.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.canoo.dp.impl.platform.core.http.HttpHeaderConstants.CONTENT_TYPE_HEADER;
import static com.canoo.dp.impl.platform.core.http.HttpHeaderConstants.FORM_MIME_TYPE;
import static com.canoo.dp.impl.platform.core.http.HttpHeaderConstants.TEXT_MIME_TYPE;
import static com.canoo.dp.impl.platform.core.http.HttpStatus.SC_HTTP_UNAUTHORIZED;
import static com.canoo.dp.impl.security.SecurityConfiguration.APPLICATION_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityConfiguration.AUTH_ENDPOINT_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.AUTH_ENDPOINT_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityConfiguration.DIRECT_CONNECTION_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.DIRECT_CONNECTION_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityConfiguration.REALM_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityHttpHeader.REALM_NAME_HEADER;
import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.19.0", status = INTERNAL)
public class KeycloakSecurity implements Security {

    private final static long TOKEN_EXPIRES_DELTA = 10_000;

    private final static long MIN_TOKEN_EXPIRES_RUN = 30_000;

    private static final Logger LOG = LoggerFactory.getLogger(KeycloakSecurity.class);

    private final String authEndpoint;

    private final String realmName;

    private final String appName;

    private final ExecutorService executor;

    private final boolean directConnect;

    private Future<Void> refreshTask;

    private final Lock refreshLock = new ReentrantLock();

    private final AtomicBoolean authorized;

    private final AtomicReference<String> accessToken;

    public KeycloakSecurity(final ClientConfiguration configuration) {
        Assert.requireNonNull(configuration, "configuration");
        this.appName = configuration.getProperty(APPLICATION_PROPERTY_NAME);
        this.authEndpoint = configuration.getProperty(AUTH_ENDPOINT_PROPERTY_NAME, AUTH_ENDPOINT_PROPERTY_DEFAULT_VALUE);
        Assert.requireNonBlank(authEndpoint, "authEndpoint");
        this.realmName = configuration.getProperty(REALM_PROPERTY_NAME);
        this.directConnect = configuration.getBooleanProperty(DIRECT_CONNECTION_PROPERTY_NAME, DIRECT_CONNECTION_PROPERTY_DEFAULT_VALUE);
        this.executor = configuration.getBackgroundExecutor();
        Assert.requireNonNull(executor, "executor");
        this.authorized = new AtomicBoolean(false);
        this.accessToken = new AtomicReference<>(null);
    }

    @Override
    public Future<Void> login(final String user, final String password) {
        if(authorized.get()) {
            throw new DolphinRuntimeException("Already logged in!");
        }
        return (Future<Void>) executor.submit(() -> {
            try {
                KeycloakOpenidConnectResult connectResult = receiveTokenByLogin(user, password);
                accessToken.set(connectResult.getAccess_token());
                authorized.set(true);
                startTokenRefreshRunner(connectResult);
            } catch (IOException | URISyntaxException e) {
                throw new DolphinRuntimeException("Can not receive security token!", e);
            }
        });
    }

    @Override
    public Future<Void> logout() {
        return executor.submit(() -> {
            authorized.set(false);
            refreshLock.lock();
            try {
                refreshTask.cancel(true);
            } finally {
                refreshLock.unlock();
            }
            accessToken.set(null);
        }, null);
    }

    @Override
    public boolean isAuthorized() {
        //REST Endpoint at Keycloak site -> Call must be done manually
        //http://www.keycloak.org/docs-api/3.3/rest-api/index.html
        //See /admin/realms/{realm}/users/{id}/sessions
        return authorized.get();
    }

    @Override
    public String getAccessToken() {
        return accessToken.get();
    }

    private void startTokenRefreshRunner(final KeycloakOpenidConnectResult connectResult) {
        refreshLock.lock();
        try {
            Assert.requireNonNull(connectResult, "connectResult");
            refreshTask = executor.submit(() -> {
                try {
                    final AtomicReference<KeycloakOpenidConnectResult> connectResultReference = new AtomicReference<>(connectResult);
                    while (!Thread.interrupted()) {
                        final KeycloakOpenidConnectResult currentConnectResult = connectResultReference.get();
                        Assert.requireNonNull(currentConnectResult, "currentConnectResult");
                        final long sleepTime = Math.max(MIN_TOKEN_EXPIRES_RUN, currentConnectResult.getExpires_in() - TOKEN_EXPIRES_DELTA);
                        Thread.sleep(sleepTime);
                        LOG.debug("Token refresh started");
                        final KeycloakOpenidConnectResult newConnectResult = receiveTokenByRefresh(currentConnectResult.getRefresh_token());
                        Assert.requireNonNull(newConnectResult, "newConnectResult");
                        accessToken.set(newConnectResult.getAccess_token());
                        LOG.debug("Token refresh done");
                        connectResultReference.set(newConnectResult);
                    }
                } catch (InterruptedException e) {
                    LOG.debug("Token refresh runner stopped");
                } catch (IOException | URISyntaxException e) {
                    throw new DolphinRuntimeException("Can not receive security token!", e);
                }
            }, null);
        } finally {
            refreshLock.unlock();
        }
    }

    private HttpClientConnection createDirectConnection() throws URISyntaxException, IOException {
        final URI url = new URI(authEndpoint + "/auth/realms/" + realmName + "/protocol/openid-connect/token");
        final HttpClientConnection clientConnection = new HttpClientConnection(url, RequestMethod.POST);
        clientConnection.addRequestHeader(CONTENT_TYPE_HEADER, FORM_MIME_TYPE);
        return clientConnection;
    }

    private HttpClientConnection createServerProxyConnection() throws URISyntaxException, IOException {
        final URI url = new URI(authEndpoint);
        final HttpClientConnection clientConnection = new HttpClientConnection(url, RequestMethod.POST);
        clientConnection.addRequestHeader(CONTENT_TYPE_HEADER, TEXT_MIME_TYPE);
        if (realmName != null && !realmName.isEmpty()) {
            clientConnection.addRequestHeader(REALM_NAME_HEADER, realmName);
        }
        return clientConnection;
    }

    private KeycloakOpenidConnectResult receiveTokenByLogin(final String user, final String password) throws IOException, URISyntaxException {
        if (directConnect) {
            final String content = "client_id=" + appName + "&username=" + user + "&password=" + password + "&grant_type=password";
            return receiveToken(createDirectConnection(), content);
        } else {
            final String content = "username=" + user + "&password=" + password + "&grant_type=password";
            return receiveToken(createServerProxyConnection(), content);
        }
    }

    private KeycloakOpenidConnectResult receiveTokenByRefresh(final String refreshToken) throws IOException, URISyntaxException {
        if (directConnect) {
            final String content = "grant_type=refresh_token&refresh_token=" + refreshToken + "&client_id=" + appName;
            return receiveToken(createDirectConnection(), content);
        } else {
            final String content = "grant_type=refresh_token&refresh_token=" + refreshToken;
            return receiveToken(createServerProxyConnection(), content);
        }
    }

    private KeycloakOpenidConnectResult receiveToken(final HttpClientConnection connection, final String content) throws IOException {
        Assert.requireNonNull(content, "content");
        LOG.debug("receiving new token from keycloak server");
        connection.setDoOutput(true);
        connection.writeRequestContent(content);
        final int responseCode = connection.readResponseCode();
        if (responseCode == SC_HTTP_UNAUTHORIZED) {
            throw new DolphinRuntimeException("Invalid login!");
        }
        final String input = connection.readUTFResponseContent();
        final Gson gson = PlatformClient.getService(Gson.class);
        final KeycloakOpenidConnectResult result = gson.fromJson(input, KeycloakOpenidConnectResult.class);
        return result;
    }

}
