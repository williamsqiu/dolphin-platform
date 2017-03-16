package com.canoo.dolphin.server.controller;

public class ControllerValidationException extends Exception {

    /**
     * Default constructor
     */
    public ControllerValidationException() {
    }

    /**
     * Constructor
     * @param message the message
     */
    public ControllerValidationException(String message) {
        super(message);
    }

    /**
     * Constructor
     * @param message the message
     * @param cause the cause
     */
    public ControllerValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     * @param cause the cause
     */
    public ControllerValidationException(Throwable cause) {
        super(cause);
    }
}
