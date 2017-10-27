package com.canoo.platform.core.http;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
public interface HttpRequest {

    HttpResponse withContent(byte[] content);

    HttpResponse withContent(byte[] content, String contentType);

    HttpResponse withContent(String content);

    HttpResponse withContent(String content, String contentType);

    <I> HttpResponse withContent(I content);

    HttpResponse withoutContent();

}
