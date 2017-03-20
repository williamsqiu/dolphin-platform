package com.canoo.dolphin.client.async.athomsphere;

import com.canoo.dolphin.client.async.AsyncClient;
import com.canoo.dolphin.client.async.AsyncConnection;
import com.canoo.dolphin.client.async.AsyncHandler;
import org.atmosphere.wasync.*;
import org.atmosphere.wasync.serial.SerializedClient;

import java.io.IOException;
import java.net.URI;

public class AthmosClient implements AsyncClient {

    @Override
    public AsyncConnection connect(final URI endpoint, final AsyncHandler handler) throws IOException {
        SerializedClient client = ClientFactory.getDefault().newClient(SerializedClient.class);

        RequestBuilder requestBuilder = client.newRequestBuilder()
                .uri(endpoint.toString())
                .transport(Request.TRANSPORT.WEBSOCKET)
                .transport(Request.TRANSPORT.SSE)
                .transport(Request.TRANSPORT.STREAMING)
                .transport(Request.TRANSPORT.LONG_POLLING);
        Request request = requestBuilder.build();

        final Socket socket = client.create();

        AsyncConnection asyncConnection = new AthmosConnection(socket);

        socket.on(Event.ERROR.name(), new Function<Throwable>() {
            @Override
            public void on(Throwable t) {
                handler.onError(asyncConnection, t);
            }
        }).on(Event.CLOSE.name(), new Function<String>() {
            @Override
            public void on(String t) {
               handler.onClose(asyncConnection);
            }
        }).on(Event.OPEN.name(), new Function<String>() {
            @Override
            public void on(String t) {
                handler.onConnect(asyncConnection);
            }
        }).on(Event.MESSAGE.name(), new Function<String>() {
            @Override
            public void on(String t) {
                handler.onMessage(asyncConnection, t);
            }
        }).open(request);

        return asyncConnection;
    }
}
