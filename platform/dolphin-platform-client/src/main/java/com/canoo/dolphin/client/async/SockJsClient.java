package com.canoo.dolphin.client.async;

import com.canoo.dolphin.util.Assert;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SockJsClient {

    private static final Logger LOG = LoggerFactory.getLogger(SockJsClient.class);

    private final Executor backgroundExecutor;

    public SockJsClient() {
        this(Executors.newCachedThreadPool());
    }

    public SockJsClient(final Executor backgroundExecutor) {
        this.backgroundExecutor = Assert.requireNonNull(backgroundExecutor, "backgroundExecutor");
    }

    public String getServerId(final UUID uuid) {
        return String.valueOf(Math.abs(uuid.getMostSignificantBits()) % 1000);
    }

    public String getSessionId(final UUID uuid) {
        return uuid.toString().replace("-", "");
    }

    public URL getInfoUrl(final URL endPointUrl) throws URISyntaxException, MalformedURLException {
        return UriComponentsBuilder.fromUri(endPointUrl.toURI())
                .scheme("http")
                .pathSegment("info")
                .build(true).toUri().toURL();
    }

    private SockJsServerInfo getSockJsInfo(final URL infoUrl) throws Exception {
        Assert.requireNonNull(infoUrl, "infoUrl");
        HttpURLConnection handshakeRequest = (HttpURLConnection) infoUrl.openConnection();
        Gson gson = new Gson();
        return gson.fromJson(new InputStreamReader(handshakeRequest.getInputStream()), SockJsServerInfo.class);
    }

    public Future<SockJsSession> connect(final SockJsHandler handler, final URL endPointUrl) {
        Assert.requireNonNull(handler, "handler");
        Assert.requireNonNull(endPointUrl, "endPointUrl");

        SettableFuture<SockJsSession> future = SettableFuture.create();

        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL infoUrl = getInfoUrl(endPointUrl);
                    final SockJsServerInfo serverInfo = getSockJsInfo(infoUrl);
                    final UUID uuid = UUID.randomUUID();
                    final String serverId = getServerId(uuid);
                    final String sessionId = getSessionId(uuid);

                    SockJsProtocol protocol = new NioSockJsProtocol();
                    SockJsSession session = protocol.connect(endPointUrl, serverId, sessionId, handler, backgroundExecutor);
                    future.set(session);
                } catch (Exception e) {
                    future.setException(e);
                }

            }
        });
        return future;
    }

    public static void main(String[] args) throws Exception {
        new SockJsClient().connect(new SockJsHandler() {
            @Override
            public void afterConnectionEstablished(SockJsSession session) throws Exception {

            }

            @Override
            public void handleMessage(SockJsSession session, Object message) throws Exception {

            }

            @Override
            public void handleTransportError(SockJsSession session, Throwable exception) throws Exception {

            }

            @Override
            public void afterConnectionClosed(SockJsSession session) throws Exception {

            }
        }, new URL("http://localhost:4444/todo-app/dolphin")).get();
    }

}
