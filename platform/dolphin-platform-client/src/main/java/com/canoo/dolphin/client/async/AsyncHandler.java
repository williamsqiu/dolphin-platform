package com.canoo.dolphin.client.async;

public interface AsyncHandler {

    void onConnect(AsyncConnection connection);

    void onClose(AsyncConnection connection);

    void onMessage(AsyncConnection connection, String message);

    void onError(AsyncConnection connection, Throwable error);
}
