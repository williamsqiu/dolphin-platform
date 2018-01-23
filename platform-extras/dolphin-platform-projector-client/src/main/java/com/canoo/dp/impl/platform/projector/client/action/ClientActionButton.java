package com.canoo.dp.impl.platform.projector.client.action;

import com.canoo.dp.impl.platform.projector.action.ClientAction;
import com.canoo.dp.impl.platform.projector.client.base.ClientActionSupport;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ClientActionButton<T> extends AbstractActionButton<ClientAction<T>> {

    private final ClientActionSupport clientActionSupport;

    public ClientActionButton(ClientAction<T> action, ClientActionSupport clientActionSupport) {
        super(action);
        this.clientActionSupport = clientActionSupport;
    }

    @Override
    protected CompletableFuture<Void> callAction() {
        Supplier<CompletableFuture<T>> actionSupplier = clientActionSupport.getActionSupplier(getAction().getActionName());
        if(actionSupplier == null) {
            throw new NullPointerException("No action found!");
        }
        return actionSupplier.get().whenComplete((t, e) -> {
            if(e != null) {
                //TODO EXCEPTION
            }
            getAction().setResult(t);
        }).thenApply(t -> null);
    }
}

