package com.canoo.platform.core.http;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
public interface HttpCallResponseBuilder {

    HttpCallExecutor<ByteArrayProvider> readBytes();

    HttpCallExecutor<ByteArrayProvider> readBytes(String contentType);

    HttpCallExecutor<String> readString();

    HttpCallExecutor<String> readString(String contentType);

    <R> HttpCallExecutor<R> readObject(Class<R> responseType);

    HttpCallExecutor<Void> withoutResult();
}
