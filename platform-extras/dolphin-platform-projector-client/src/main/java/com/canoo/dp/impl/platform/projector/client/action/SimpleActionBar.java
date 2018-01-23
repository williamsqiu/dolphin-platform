package com.canoo.dp.impl.platform.projector.client.action;

import com.canoo.dp.impl.platform.projector.action.Action;
import com.canoo.dp.impl.platform.projector.client.projection.Projector;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.client.ControllerProxy;
import javafx.scene.layout.FlowPane;

public class SimpleActionBar extends FlowPane {

    private final ObservableList<Action> actions;

    private final ControllerProxy controllerProxy;

    private final Projector projector;

    public SimpleActionBar(ControllerProxy controllerProxy, ObservableList<Action> actions, Projector projector) {
        this.actions = actions;
        this.controllerProxy = controllerProxy;
        this.projector = projector;
        getStyleClass().add("action-bar");
        this.actions.onChanged( e -> updateActions());
        updateActions();
    }

    protected void updateActions() {
        getChildren().clear();
        for (Action action : actions) {
            getChildren().add(projector.create(action, controllerProxy));
        }
    }

}
