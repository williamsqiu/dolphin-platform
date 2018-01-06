package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.http.HttpHeader;

public class HttpHeaderImpl implements HttpHeader {

    private final String name;

    private final String content;

    public HttpHeaderImpl(final String name, final String content) {
        this.name = Assert.requireNonBlank(name, "name");
        this.content = Assert.requireNonBlank(content, "content");
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
