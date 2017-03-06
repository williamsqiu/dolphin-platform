package com.canoo.dolphin.client.async;

public interface SockJsHandler {

    void afterConnectionEstablished(SockJsSession session) throws Exception;

    void handleMessage(SockJsSession session, Object message) throws Exception;

    void handleTransportError(SockJsSession session, Throwable exception) throws Exception;

    void afterConnectionClosed(SockJsSession session) throws Exception;

}
