package com.canoo.platform.core.http;

public interface HttpResponse {

    HttpExecutor<ByteArrayProvider> readBytes();

    HttpExecutor<ByteArrayProvider> readBytes(String contentType);

    HttpExecutor<String> readString();

    HttpExecutor<String> readString(String contentType);

    <R> HttpExecutor<R> readObject(Class<R> responseType);

    HttpExecutor<Void> withoutResult();
}
