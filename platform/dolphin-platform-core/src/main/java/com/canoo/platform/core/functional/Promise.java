package com.canoo.platform.core.functional;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface Promise<V, T extends Throwable> {

    Promise<V, T> onDone(Consumer<V> task);

    Promise<V, T> onError(Consumer<T> errorHandler);

    CompletableFuture<V> execute();
}
