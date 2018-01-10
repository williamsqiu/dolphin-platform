package com.canoo.platform.core.functional;

import java.util.concurrent.CompletableFuture;

public interface ExecutablePromise<V, T extends Throwable> extends Promise<V, T> {

    CompletableFuture<V> execute();
}
