package com.canoo.dp.impl.platform.projector.client.base;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface ClientActionSupport {

    <T> void addClientAction(String actionName, Supplier<CompletableFuture<T>> actionSupplier);

    default <T> void addClientActionSupplier(String actionName, Supplier<T> actionSupplier) {
        addClientAction(actionName, () -> {
            CompletableFuture<T> ret = new CompletableFuture<T>();
            ret.complete(actionSupplier.get());
            return ret;
        });

    }

    <T> Supplier<CompletableFuture<T>> getActionSupplier(String actionName);
}
