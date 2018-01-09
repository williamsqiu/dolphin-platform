package com.canoo.platform.core.http;

public class BadEndpointException extends HttpException {

    public BadEndpointException(String message, Throwable cause) {
        super(message, cause);
    }
}
