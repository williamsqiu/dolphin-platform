package com.canoo.dolphin.server.bootstrap;

public class InitializationException extends Exception {

    public InitializationException(String message) {
        super(message);
    }

    public InitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
