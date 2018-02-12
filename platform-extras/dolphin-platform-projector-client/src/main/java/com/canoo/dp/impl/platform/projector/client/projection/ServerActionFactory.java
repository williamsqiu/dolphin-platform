package com.canoo.dp.impl.platform.projector.client.projection;

import com.canoo.dp.impl.platform.projector.action.ServerAction;
import com.canoo.dp.impl.platform.projector.client.action.ServerActionButton;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ControllerProxy;
import javafx.scene.Parent;

public class ServerActionFactory implements ProjectionFactory<ServerAction> {

    @Override
    public Parent createProjection(Projector projector, ClientContext clientContext, ControllerProxy controllerProxy, ServerAction projectable) {
        return new ServerActionButton(controllerProxy, projectable);
    }
}
