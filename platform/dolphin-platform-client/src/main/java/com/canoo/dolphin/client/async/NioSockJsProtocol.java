package com.canoo.dolphin.client.async;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.Executor;

public class NioSockJsProtocol implements SockJsProtocol {

    private final static String SEND_PROTOCOL = "xhr_send";

    private final static String STREAMING_PROTOCOL = "xhr_streaming";

    private URL baseEndpoint;

    private String serverId;

    private String sessionId;

    private SockJsHandler handler;

    @Override
    public SockJsSession connect(final URL baseEndpoint, final String serverId, final String sessionId, final SockJsHandler handler, final Executor executor) throws IOException, URISyntaxException {
        this.baseEndpoint = baseEndpoint;
        this.serverId = serverId;
        this.sessionId = sessionId;
        this.handler = handler;

        final SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(baseEndpoint.getHost(), baseEndpoint.getPort()));

        // TODO: Looks like the XHR communication starts with a request to the xhr_streaming endpoint
        // This request should happen here

        SockJsSession session = new SockJsSession() {
            @Override
            public String getId() {
                return sessionId;
            }

            @Override
            public URL getUri() {
                return baseEndpoint;
            }

            @Override
            public String getAcceptedProtocol() {
                return SEND_PROTOCOL;
            }

            @Override
            public void sendMessage(String message) throws IOException {
                sendMessageToChannel(this, message, channel, executor);
            }

            @Override
            public boolean isOpen() {
                return channel.isOpen();
            }

            @Override
            public void close() throws IOException {
                try {
                    closeChannel(channel, this);
                } catch (IOException e) {
                    handler.handleTransportError(this, e);
                    throw e;
                }
            }
        };

        executor.execute(() -> {
            try {
                readFromChannel(channel);
            } catch (IOException e) {
                handler.handleTransportError(session, e);
            }
        });

        handler.afterConnectionEstablished(session);
        return session;
    }

    private void closeChannel(SocketChannel channel, SockJsSession session) throws IOException {
        channel.close();
        handler.afterConnectionClosed(session);
    }

    private void addReceivedBytes(final byte[] bytes) {
        //TODO: Create HTTP(S) request based on the received bytes (can be called several times)
    }

    private void readFromChannel(final ReadableByteChannel channel) throws IOException {
        int read = 0;
        ByteBuffer inputBuffer = ByteBuffer.allocate(48);
        while (read >= 0 && channel.isOpen()) {
            read = channel.read(inputBuffer);
            addReceivedBytes(inputBuffer.array());
            inputBuffer.clear();
        }
    }

    private void sendMessageToChannel(final SockJsSession session, final String message, final WritableByteChannel channel, final Executor executor) {
        executor.execute(() -> {
            try {

                //TODO: Create HTTP(S) request with message as content

                ByteBuffer inputBuffer = ByteBuffer.wrap(message.getBytes());
                inputBuffer.flip();
                while (inputBuffer.hasRemaining()) {
                    channel.write(inputBuffer);
                }
                inputBuffer.clear();
            } catch (Exception e) {
              handler.handleTransportError(session, e);
            }
        });
    }

    private URL getTransportUrl(final String protocol) throws URISyntaxException, MalformedURLException {
        return UriComponentsBuilder.fromUri(baseEndpoint.toURI())
                .scheme(baseEndpoint.toURI().getScheme())
                .pathSegment(serverId)
                .pathSegment(sessionId)
                .pathSegment(protocol)
                .build(true).toUri().toURL();
    }
}
