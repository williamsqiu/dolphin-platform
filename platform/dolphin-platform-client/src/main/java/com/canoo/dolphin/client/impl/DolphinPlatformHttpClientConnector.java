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
import com.canoo.dolphin.client.HttpURLConnectionFactory;
import com.canoo.dolphin.client.HttpURLConnectionResponseHandler;
import com.canoo.dolphin.util.Assert;
import com.canoo.dolphin.util.DolphinRemotingException;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.comm.AbstractClientConnector;
import org.opendolphin.core.client.comm.BlindCommandBatcher;
import org.opendolphin.core.client.comm.ExceptionHandler;
import org.opendolphin.core.comm.Codec;
import org.opendolphin.core.comm.Command;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.net.CookieStore;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class is used to sync the unique client scope id of the current dolphin
 */
public class DolphinPlatformHttpClientConnector extends AbstractClientConnector {

    private static final String CHARSET = "UTF-8";

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private static final String ACCEPT_HEADER = "Accept";

    private static final String COOKIE_HEADER = "Cookie";

    private static final String SET_COOKIE_HEADER = "Set-Cookie";

    private static final String POST_METHOD = "POST";

    private static final String JSON_MIME_TYPE = "application/json";

    private final URL servletUrl;

    private final ForwardableCallback<DolphinRemotingException> remotingErrorHandler;

    private final Codec codec;

    private final CookieStore cookieStore;

    private final HttpURLConnectionFactory connectionFactory;

    private final HttpURLConnectionResponseHandler responseHandler;

    private String clientId;

    private DolphinPlatformWebSocketHandler webSocketHandler;

    public DolphinPlatformHttpClientConnector(ClientConfiguration configuration, ClientDolphin clientDolphin, Codec codec, ForwardableCallback<DolphinRemotingException> remotingErrorHandler, ExceptionHandler onException) {
        super(clientDolphin, Assert.requireNonNull(configuration, "configuration").getUiExecutor(), new BlindCommandBatcher(), onException, configuration.getBackgroundExecutor());
        setStrictMode(false);

        this.servletUrl = configuration.getServerEndpoint();

        this.connectionFactory = configuration.getConnectionFactory();
        this.cookieStore = configuration.getCookieStore();
        this.responseHandler = configuration.getResponseHandler();

        this.codec = Assert.requireNonNull(codec, "codec");
        this.remotingErrorHandler = Assert.requireNonNull(remotingErrorHandler, "remotingErrorHandler");


        List<Transport> transports = new ArrayList<>(2);
        //transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());
        webSocketHandler = new DolphinPlatformWebSocketHandler();

        sockJsClient.doHandshake(webSocketHandler, servletUrl.toString());
    }

    public List<Command> transmit(List<Command> commands) {
        List<Command> result = webSocketHandler.transmit(commands);
        return result;
    }

    public class DolphinPlatformWebSocketHandler implements WebSocketHandler {

        private CompletableFuture<WebSocketSession> session = new CompletableFuture<>();

        private CompletableFuture<List<Command>> result = new CompletableFuture<>();

        public List<Command> transmit(List<Command> commands) {
            try {
                session.get().sendMessage(new TextMessage(codec.encode(commands)));
                result = new CompletableFuture<>();
                return result.get();
            } catch (Exception e) {
                DolphinRemotingException dolphinRemotingException = new DolphinRemotingException("Error in remoting layer", e);
                remotingErrorHandler.call(dolphinRemotingException);
                throw dolphinRemotingException;
            }
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            this.session.complete(session);
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            result.complete(codec.decode(message.getPayload().toString()));
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            result.completeExceptionally(exception);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        }

        @Override
        public boolean supportsPartialMessages() {
            return false;
        }
    }

}



