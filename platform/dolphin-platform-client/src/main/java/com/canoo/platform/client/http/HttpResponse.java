package com.canoo.platform.client.http;

import java.io.IOException;

public interface HttpResponse {

    byte[] readBytes() throws IOException;

    String readString() throws IOException;

    <R> R readObject(Class<R> responseType) throws IOException;

    void handle() throws IOException;
}
