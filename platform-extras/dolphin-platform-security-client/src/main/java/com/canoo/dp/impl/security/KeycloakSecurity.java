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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.canoo.dp.impl.platform.core.http.HttpStatus.SC_HTTP_UNAUTHORIZED;
import static com.canoo.dp.impl.security.SecurityConfiguration.APPLICATION_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.APPLICATION_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityConfiguration.AUTH_ENDPOINT_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.AUTH_ENDPOINT_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityConfiguration.DIRECT_CONNECTION_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.DIRECT_CONNECTION_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityConfiguration.REALM_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.REALM_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityHttpHeader.REALM_NAME_HEADER;
import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.19.0", status = INTERNAL)
public class KeycloakSecurity implements Security {

    //Setup for the realm / client -> "Direct Grand" must be enabled in keycloak admin

    private static final Logger LOG = LoggerFactory.getLogger(KeycloakSecurity.class);

    private final String authEndpoint;

    private final String realmName;

    private final String appName;

    private final ExecutorService executor;

    private final boolean directConnect;

    private KeycloakOpenidConnectResult connectResult;

    private long tokenCreation;

    public KeycloakSecurity(final ClientConfiguration configuration) {
        Assert.requireNonNull(configuration, "configuration");

        this.appName =configuration.getProperty(APPLICATION_PROPERTY_NAME, APPLICATION_PROPERTY_DEFAULT_VALUE);
        Assert.requireNonBlank(appName, "appName");

        this.authEndpoint = configuration.getProperty(AUTH_ENDPOINT_PROPERTY_NAME, AUTH_ENDPOINT_PROPERTY_DEFAULT_VALUE);
        Assert.requireNonBlank(authEndpoint, "authEndpoint");

        this.realmName = configuration.getProperty(REALM_PROPERTY_NAME, REALM_PROPERTY_DEFAULT_VALUE);
        Assert.requireNonBlank(realmName, "realmName");

        this.directConnect = configuration.getBooleanProperty(DIRECT_CONNECTION_PROPERTY_NAME, DIRECT_CONNECTION_PROPERTY_DEFAULT_VALUE);

        this.executor = configuration.getBackgroundExecutor();
        Assert.requireNonNull(executor, "executor");
    }

    private HttpClientConnection createDirectConnection() throws URISyntaxException, IOException {
        final URI url = new URI(authEndpoint + "/auth/realms/" + realmName + "/protocol/openid-connect/token");
        final HttpClientConnection clientConnection = new HttpClientConnection(url, RequestMethod.POST);
        return clientConnection;
    }

    private HttpClientConnection createServerProxyConnection() throws URISyntaxException, IOException {
        final URI url = new URI(authEndpoint);
        final HttpClientConnection clientConnection = new HttpClientConnection(url, RequestMethod.POST);
        if(realmName != null && !realmName.isEmpty()) {
            clientConnection.addRequestHeader(REALM_NAME_HEADER, realmName);
        }
        return clientConnection;
    }

    private HttpClientConnection createConnection() throws IOException, URISyntaxException {
        if(directConnect) {
            return createDirectConnection();
        } else {
            return createServerProxyConnection();
        }
    }

    private void receiveTokenFromKeycloak(final String content) throws IOException, URISyntaxException {
        Assert.requireNonNull(content, "content");
        LOG.debug("receiving new token from keycloak server");
        final HttpClientConnection clientConnection = createConnection();
        clientConnection.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        clientConnection.setDoOutput(true);
        clientConnection.writeRequestContent(content);
        final int responseCode = clientConnection.readResponseCode();
        if(responseCode == SC_HTTP_UNAUTHORIZED) {
            throw new DolphinRuntimeException("Invalid login!");
        }
        final String input = clientConnection.readUTFResponseContent();
        final Gson gson = PlatformClient.getService(Gson.class);
        final KeycloakOpenidConnectResult result = gson.fromJson(input, KeycloakOpenidConnectResult.class);
        connectResult = result;
        tokenCreation = System.currentTimeMillis();
    }

    @Override
    public Future<Void> login(final String user, final String password) {
        return (Future<Void>) executor.submit(() -> {
            try {
                receiveTokenFromKeycloak("client_id=" + appName + "&username=" + user + "&password=" + password + "&grant_type=password");
            } catch (IOException | URISyntaxException e) {
                throw new DolphinRuntimeException("Can not receive security token!", e);
            }
        });
    }

    public Future<Void> refreshToken() {
        if(connectResult == null) {
            throw new IllegalStateException("Can not refresh token without valid login");
        }
        final String refreshToken = connectResult.getRefresh_token();
        return (Future<Void>) executor.submit(() -> {
                try {
                    receiveTokenFromKeycloak("grant_type=refresh_token&refresh_token=" + refreshToken + "&client_id=" + appName);
                } catch (IOException | URISyntaxException e) {
                    throw new DolphinRuntimeException("Can not refresh security token!", e);
                }
            });
    }

    public long tokenExpiresAt() {
        if (connectResult == null) {
            return -1;
        }
        return tokenCreation + connectResult.getExpires_in() * 1000;
    }

    @Override
    public Future<Void> logout() {
        //TODO: currently there is no perfect solution at keycloak to do such a logout
        connectResult = null;
        CompletableFuture<Void> resultHack = new CompletableFuture<>();
        resultHack.complete(null);
        return resultHack;
    }

    public boolean isAuthorized() {
        //REST Endpoint at Keycloak site -> Call must be done manually
        //http://www.keycloak.org/docs-api/3.3/rest-api/index.html
        //See /admin/realms/{realm}/users/{id}/sessions
        return connectResult != null;
    }

    public String getAccessToken() {
        if (connectResult == null) {
            return null;
        }
        return connectResult.getAccess_token();
    }
}
