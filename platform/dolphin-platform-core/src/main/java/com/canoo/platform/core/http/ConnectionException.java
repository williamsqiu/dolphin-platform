package com.canoo.platform.core.http;

/**
 * Created by hendrikebbers on 09.01.18.
 */
public class ConnectionException extends HttpException {

    public ConnectionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
