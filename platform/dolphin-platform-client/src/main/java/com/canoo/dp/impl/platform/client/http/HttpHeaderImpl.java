package com.canoo.dp.impl.platform.client.http;

import com.canoo.platform.core.http.HttpHeader;

public class HttpHeaderImpl implements HttpHeader {

    private final String name;

    private final String content;

    public HttpHeaderImpl(final String name, final String content) {
        //No null checks since the HTTP version is defined as header with no / null name
        this.name = name;
        this.content = content;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContent() {
        return content;
    }
}
