package com.canoo.dolphin.client.async;

public interface SockJsHandler {

    void afterConnectionEstablished(SockJsSession session);

    void handleMessage(SockJsSession session, Object message);

    void handleTransportError(SockJsSession session, Throwable exception);

    void afterConnectionClosed(SockJsSession session);

}
