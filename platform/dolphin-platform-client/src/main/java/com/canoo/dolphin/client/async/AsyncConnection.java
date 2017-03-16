package com.canoo.dolphin.client.async;

import java.io.IOException;

public interface AsyncConnection {

    void disconnect();

    String getTransport();

    void send(String message) throws IOException;
}
