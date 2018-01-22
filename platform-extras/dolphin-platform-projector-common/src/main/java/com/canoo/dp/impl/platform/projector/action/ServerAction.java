package com.canoo.dp.impl.platform.projector.action;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface ServerAction extends Action {

    Property<String> actionNameProperty();

    default String getActionName() {
        return actionNameProperty().get();
    }

    default void setActionName(String actionName) {
        actionNameProperty().set(actionName);
    }

}
