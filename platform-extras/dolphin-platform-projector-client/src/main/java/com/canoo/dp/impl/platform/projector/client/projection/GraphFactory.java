package com.canoo.dp.impl.platform.projector.client.projection;

import com.canoo.dp.impl.platform.projector.client.graph.GraphComponent;
import com.canoo.dp.impl.platform.projector.graph.GraphDataBean;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ControllerProxy;
import javafx.scene.Parent;

public class GraphFactory implements ProjectionFactory<GraphDataBean> {

    @Override
    public Parent createProjection(Projector projector, ClientContext clientContext, ControllerProxy controllerProxy, GraphDataBean projectable) {
        return new GraphComponent(projectable);
    }
}

