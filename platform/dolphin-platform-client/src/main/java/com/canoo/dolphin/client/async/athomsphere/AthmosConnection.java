package com.canoo.dolphin.client.async.athomsphere;

import com.canoo.dolphin.client.async.AsyncConnection;
import org.atmosphere.wasync.Event;
import org.atmosphere.wasync.Function;
import org.atmosphere.wasync.Socket;

import java.io.IOException;

public class AthmosConnection implements AsyncConnection {

    private final Socket socket;

    private String transport;

    public AthmosConnection(final Socket socket) {
        this.socket = socket;
        socket.on(Event.TRANSPORT.name(), new Function<String>() {
            @Override
            public void on(String t) {
                transport = t;
            }
        });
    }

    @Override
    public void disconnect() {
        socket.close();
    }

    @Override
    public String getTransport() {
        return transport;
    }

    @Override
    public void send(final String message) throws IOException {
        socket.fire(message);
    }
}
