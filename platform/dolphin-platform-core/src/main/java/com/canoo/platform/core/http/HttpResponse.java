package com.canoo.platform.core.http;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
public interface HttpResponse {

    HttpExecutor<ByteArrayProvider> readBytes();

    HttpExecutor<ByteArrayProvider> readBytes(String contentType);

    HttpExecutor<String> readString();

    HttpExecutor<String> readString(String contentType);

    <R> HttpExecutor<R> readObject(Class<R> responseType);

    HttpExecutor<Void> withoutResult();
}
