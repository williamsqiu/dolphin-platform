package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.http.HttpHeader;
import com.canoo.platform.core.http.HttpResponse;

import java.util.Collections;
import java.util.List;

public class HttpResponseImpl<V> implements HttpResponse<V> {

    private final List<HttpHeader> headers;

    private final int statusCode;

    private final byte[] rawContent;

    private final V content;

    public HttpResponseImpl(final List<HttpHeader> headers, final int statusCode, final byte[] rawContent, final V content) {
        this.headers = Collections.unmodifiableList(Assert.requireNonNull(headers, "headers"));
        this.statusCode = statusCode;

        this.rawContent = rawContent;
        this.content = content;
    }

    @Override
    public List<HttpHeader> getHeaders() {
        return headers;
    }

    @Override
    public V getContent() {
        return content;
    }

    @Override
    public byte[] getRawContent() {
        return rawContent;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
