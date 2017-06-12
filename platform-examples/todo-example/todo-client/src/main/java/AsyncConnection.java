import com.canoo.dolphin.util.Assert;
import org.atmosphere.wasync.*;
import org.atmosphere.wasync.impl.AtmosphereClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;

public class AsyncConnection {

    Logger LOG = LoggerFactory.getLogger(AsyncConnection.class);

    private final Request request;

    private final Client client;

    private Socket socket;

    public AsyncConnection(final String endpoint, final boolean ws, final boolean sse, boolean stream) {
        Assert.requireNonBlank(endpoint, "endpoint");
        client = ClientFactory.getDefault().newClient(AtmosphereClient.class);

        final RequestBuilder requestBuilder = client.newRequestBuilder().
                uri(endpoint);
        if (ws) {
            requestBuilder.transport(Request.TRANSPORT.WEBSOCKET);
        }
        if (sse) {
            requestBuilder.transport(Request.TRANSPORT.SSE);
        }
        if (stream) {
            requestBuilder.transport(Request.TRANSPORT.STREAMING);
        }
        requestBuilder.transport(Request.TRANSPORT.LONG_POLLING);
        request = requestBuilder.build();
    }

    public void connect() throws IOException {
        socket = client.create();
        socket.on(Event.OPEN, s -> onOpen()).
                on(Event.CLOSE.name(), s -> onClose()).
                on(Event.REOPENED, s -> onReopen()).
                on(Event.TRANSPORT, t -> onTransport((String) t)).
                on(Event.MESSAGE, s -> onMessage((String) s)).
                on(Event.ERROR, t -> onError((Throwable) t)).
                open(request);
    }

    private void onTransport(String transport) {
        LOG.info("TRANSPORT - {}", transport);
    }

    public void onOpen() {
        LOG.info("OPEN");
    }

    public void onClose() {
        LOG.info("CLOSE");
    }

    public void onReopen() {
        LOG.info("REOPEN");
    }

    public void onMessage(String message) {
        LOG.info("MESSAGE - {}", message);
        send("Pong");
    }

    public void onError(Throwable t) {
        LOG.info("ERROR");
    }

    public void send(String message) {
        try {
            LOG.info("SENDING - " + message);
            socket.fire(message);
        } catch (Exception e) {
            throw new RuntimeException("Error in send", e);
        }
    }

    public static void main(String[] args) throws Exception {
        final String endpoint = "http://127.0.0.1:8080/todo-app/remoting";

        final AsyncConnection connection = new AsyncConnection(endpoint, false, false, false);
        connection.connect();

        Executors.newSingleThreadExecutor().execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connection.send("HUHU");
            }
        });
    }
}
