package com.canoo.platform.core.http;

import com.canoo.platform.core.functional.ExecutablePromise;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
public interface HttpCallResponseBuilder {

    ExecutablePromise<HttpResponse<ByteArrayProvider>, HttpException> readBytes();

    ExecutablePromise<HttpResponse<ByteArrayProvider>, HttpException> readBytes(String contentType);

    ExecutablePromise<HttpResponse<String>, HttpException> readString();

    ExecutablePromise<HttpResponse<String>, HttpException> readString(String contentType);

    <R> ExecutablePromise<HttpResponse<R>, HttpException> readObject(Class<R> responseType);

    ExecutablePromise<HttpResponse<Void>, HttpException> withoutResult();
}
