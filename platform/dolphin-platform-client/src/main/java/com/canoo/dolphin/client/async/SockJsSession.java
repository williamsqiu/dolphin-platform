package com.canoo.dolphin.client.async;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;

public interface SockJsSession extends Closeable {

    String getId();

    URL getUri();

    String getAcceptedProtocol();

    void sendMessage(String message) throws IOException;

    boolean isOpen();
}
