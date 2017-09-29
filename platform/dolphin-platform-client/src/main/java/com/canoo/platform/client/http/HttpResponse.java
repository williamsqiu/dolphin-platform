package com.canoo.platform.client.http;

import java.io.IOException;

public interface HttpResponse {

    byte[] readBytes() throws IOException;

    byte[] readBytes(String contentType) throws IOException;

    String readString() throws IOException;

    String readString(String contentType) throws IOException;

    <R> R readObject(Class<R> responseType) throws IOException;

    void handle() throws IOException;
}
