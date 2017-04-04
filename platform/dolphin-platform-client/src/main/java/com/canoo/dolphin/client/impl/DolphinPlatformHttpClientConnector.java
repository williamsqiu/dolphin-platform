/*
 * Copyright 2015-2017 Canoo Engineering AG.
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
package com.canoo.dolphin.client.impl;

import com.canoo.dolphin.client.ClientConfiguration;
import com.canoo.dolphin.client.DolphinSessionException;
import com.canoo.dolphin.client.HttpURLConnectionFactory;
import com.canoo.dolphin.client.HttpURLConnectionResponseHandler;
import com.canoo.dolphin.impl.PlatformConstants;
import com.canoo.dolphin.util.Assert;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.comm.AbstractClientConnector;
import org.opendolphin.core.client.comm.BlindCommandBatcher;
import org.opendolphin.core.client.comm.RemotingExceptionHandler;
import org.opendolphin.core.comm.Codec;
import org.opendolphin.core.comm.Command;
import org.opendolphin.util.DolphinRemotingException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is used to sync the unique client scope id of the current dolphin
 */
public class DolphinPlatformHttpClientConnector extends AbstractClientConnector {

    private final URL servletUrl;

    private final Codec codec;

    private final CookieStore cookieStore;

    private final HttpURLConnectionFactory connectionFactory;

    private final HttpURLConnectionResponseHandler responseHandler;

    private String clientId;

    public DolphinPlatformHttpClientConnector(ClientConfiguration configuration, ClientDolphin clientDolphin, Codec codec, RemotingExceptionHandler onException) {
        super(clientDolphin.getModelStore(), Assert.requireNonNull(configuration, "configuration").getUiExecutor(), new BlindCommandBatcher(), onException, configuration.getBackgroundExecutor());
        this.servletUrl = configuration.getServerEndpoint();
        this.connectionFactory = configuration.getConnectionFactory();
        this.cookieStore = configuration.getCookieStore();
        this.responseHandler = configuration.getResponseHandler();
        this.codec = Assert.requireNonNull(codec, "codec");

        //TODO: Strict mode is always false in DP. This flag should be removed in AbstractClientConnector
        setStrictMode(false);
    }

    public List<Command> transmit(List<Command> commands) throws DolphinRemotingException {
        Assert.requireNonNull(commands, "commands");

        try {
            //REQUEST
            final HttpURLConnection conn = connectionFactory.create(servletUrl);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty(PlatformConstants.CONTENT_TYPE_HEADER, PlatformConstants.JSON_MIME_TYPE);
            conn.setRequestProperty(PlatformConstants.ACCEPT_HEADER, PlatformConstants.JSON_MIME_TYPE);
            conn.setRequestMethod(PlatformConstants.POST_METHOD);
            if(clientId != null) {
                conn.setRequestProperty(PlatformConstants.CLIENT_ID_HTTP_HEADER_NAME, clientId);
            }
            setRequestCookies(conn);
            String content = codec.encode(commands);
            OutputStream w = conn.getOutputStream();
            w.write(content.getBytes(PlatformConstants.CHARSET));
            w.close();

            //RESPONSE
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpStatus.SC_REQUEST_TIMEOUT) {
                throw new DolphinSessionException("Server can not handle Dolphin Client ID");
            }
            if (responseCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
                throw new DolphinHttpResponseException(responseCode, conn.getResponseMessage());
            }

            responseHandler.handle(conn);

            updateCookiesFromResponse(conn);
            updateClientId(conn);
            if (commands.size() == 1 && commands.get(0) == getReleaseCommand()) {
                return new ArrayList<>();
            } else {
                String receivedContent = new String(inputStreamToByte(conn.getInputStream()), PlatformConstants.CHARSET);
                return codec.decode(receivedContent);
            }
        } catch (Exception e) {
            throw new DolphinRemotingException("Error in remoting layer", e);
        }
    }

    private void updateCookiesFromResponse(final HttpURLConnection conn) throws URISyntaxException {
        Map<String, List<String>> headerFields = conn.getHeaderFields();
        List<String> cookiesHeader = headerFields.get(PlatformConstants.SET_COOKIE_HEADER);

        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                List<HttpCookie> cookies = HttpCookie.parse(cookie);
                for(HttpCookie httpCookie : cookies) {
                    cookieStore.add(servletUrl.toURI(), httpCookie);
                }
            }
        }
    }

    private void setRequestCookies(final HttpURLConnection conn) throws URISyntaxException {
        if (cookieStore.getCookies().size() > 0) {

            String cookieValue = "";
            for(HttpCookie cookie : cookieStore.get(servletUrl.toURI())) {
                cookieValue = cookieValue + cookie + ";";
            }
            if(!cookieValue.isEmpty()) {
                cookieValue = cookieValue.substring(0, cookieValue.length());
                conn.setRequestProperty(PlatformConstants.COOKIE_HEADER, cookieValue);
            }
        }
    }

    private void updateClientId(HttpURLConnection conn) {
        String clientIdInHeader = conn.getHeaderField(PlatformConstants.CLIENT_ID_HTTP_HEADER_NAME);
        if (this.clientId != null && !this.clientId.equals(clientIdInHeader)) {
            throw new IllegalStateException("Error: client id conflict!");
        }
        this.clientId = clientIdInHeader;
    }

    private byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int read = is.read();
        while (read != -1) {
            byteArrayOutputStream.write(read);
            read = is.read();
        }
        return byteArrayOutputStream.toByteArray();
    }


}



