package com.canoo.platform.core.http;

import java.util.List;

public interface HttpResponse<V> {

    List<HttpHeader> getHeaders();

    V getContent();

    byte[] getRawContent();

    int getStatusCode();
}
