package com.canoo.dp.impl.platform.projector.client.projection;

import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ControllerProxy;
import javafx.scene.Parent;

public interface ProjectionFactory<T> {

    Parent createProjection(Projector projector, ClientContext clientContext, ControllerProxy controllerProxy, T projectable);
}
