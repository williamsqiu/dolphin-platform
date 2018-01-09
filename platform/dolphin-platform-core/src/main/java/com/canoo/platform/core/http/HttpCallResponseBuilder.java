package com.canoo.platform.core.http;

import com.canoo.platform.core.functional.Promise;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
public interface HttpCallResponseBuilder {

    Promise<HttpResponse<ByteArrayProvider>, HttpException> readBytes();

    Promise<HttpResponse<ByteArrayProvider>, HttpException> readBytes(String contentType);

    Promise<HttpResponse<String>, HttpException> readString();

    Promise<HttpResponse<String>, HttpException> readString(String contentType);

    <R> Promise<HttpResponse<R>, HttpException> readObject(Class<R> responseType);

    Promise<HttpResponse<Void>, HttpException> withoutResult();
}
