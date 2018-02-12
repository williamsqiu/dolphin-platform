package com.canoo.dp.impl.platform.projector.client.action;

import com.canoo.dp.impl.platform.projector.action.ServerAction;
import com.canoo.platform.remoting.client.ControllerProxy;

import java.util.concurrent.CompletableFuture;

public class ServerActionButton extends AbstractActionButton<ServerAction> {

    private final ControllerProxy controllerProxy;

    public ServerActionButton(ControllerProxy controllerProxy, ServerAction action) {
        super(action);
        this.controllerProxy = controllerProxy;
    }

    @Override
    protected CompletableFuture<Void> callAction() {
        return controllerProxy.invoke(getAction().getActionName());
    }
}
