package com.canoo.dp.impl.platform.projector.action;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class DefaultServerActionBean extends AbstractActionBean implements ServerAction {

    private Property<String> actionName;

    @Override
    public Property<String> actionNameProperty() {
        return actionName;
    }

}
