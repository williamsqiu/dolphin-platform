package com.canoo.platform.core.http;

import org.apiguardian.api.API;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
public interface HttpExecutor<R> {

    HttpExecutor<R> onDone(Consumer<R> task);

    HttpExecutor<R> onError(Consumer<Throwable> errorHandler);

    CompletableFuture<R> execute();
}
