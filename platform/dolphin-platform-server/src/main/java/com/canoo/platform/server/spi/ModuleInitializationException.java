package com.canoo.platform.server.spi;

public class ModuleInitializationException extends Exception {

    public ModuleInitializationException(String message) {
        super(message);
    }

    public ModuleInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
