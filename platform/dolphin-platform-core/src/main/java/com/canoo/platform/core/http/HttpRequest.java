package com.canoo.platform.core.http;

public interface HttpRequest {

    HttpResponse withContent(byte[] content);

    HttpResponse withContent(byte[] content, String contentType);

    HttpResponse withContent(String content);

    HttpResponse withContent(String content, String contentType);

    <I> HttpResponse withContent(I content);

    HttpResponse withoutContent();

}
