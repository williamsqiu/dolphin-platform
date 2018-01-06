package com.canoo.platform.core.http;

import org.apiguardian.api.API;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
public interface HttpCallExecutor<R> {

    HttpCallExecutor<R> onDone(Consumer<HttpResponse<R>> task);

    HttpCallExecutor<R> onError(BiConsumer<Throwable, HttpResponse<R>> errorHandler);

    CompletableFuture<HttpResponse<R>> execute();
}
