package com.canoo.platform.client.http;

import java.io.IOException;

public interface HttpRequest {

    HttpResponse withContent(byte[] content) throws IOException;

    HttpResponse withContent(String content) throws IOException;

    <I> HttpResponse withContent(I content) throws IOException;

    HttpResponse withoutContent() throws IOException;

}
