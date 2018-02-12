package com.canoo.dp.impl.platform.projector.client.base;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class DefaultClientActionSupport implements ClientActionSupport {

    private Map<String, Object> map = new HashMap<>();

    @Override
    public <T> void addClientAction(String actionName, Supplier<CompletableFuture<T>> actionSupplier) {
       map.put(actionName, actionSupplier);
    }

    @Override
    public <T> Supplier<CompletableFuture<T>> getActionSupplier(String actionName) {
        return (Supplier<CompletableFuture<T>>) map.get(actionName);
    }
}
