package com.canoo.platform.core.http;

public class BadResponseException extends HttpException {

    private final HttpResponse response;

    public BadResponseException(final HttpResponse response, final String message) {
        super(message);
        this.response = response;
    }

    public <V> HttpResponse<V> getResponse() {
        return response;
    }
}
