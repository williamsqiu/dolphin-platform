package com.canoo.platform.core.http;

import java.io.IOException;

public class HttpException extends IOException {

    public HttpException(String message) {
        super(message);
    }

    public HttpException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
