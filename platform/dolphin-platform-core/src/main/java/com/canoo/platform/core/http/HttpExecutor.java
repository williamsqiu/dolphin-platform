package com.canoo.platform.core.http;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface HttpExecutor<R> {

    HttpExecutor<R> onDone(Consumer<R> task);

    HttpExecutor<R> onError(Consumer<Throwable> errorHandler);

    CompletableFuture<R> execute();
}
