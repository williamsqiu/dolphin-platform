package com.canoo.dp.impl.platform.projector.client.projection;

import com.canoo.dp.impl.platform.projector.action.ClientAction;
import com.canoo.dp.impl.platform.projector.client.action.ClientActionButton;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ControllerProxy;
import javafx.scene.Parent;

public class ClientActionFactory<T> implements ProjectionFactory<ClientAction<T>> {

    @Override
    public Parent createProjection(Projector projector, ClientContext clientContext, ControllerProxy controllerProxy, ClientAction<T> projectable) {
        return new ClientActionButton<>(projectable, projector.getClientActionSupport());
    }

}
