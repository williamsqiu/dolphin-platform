package com.canoo.dp.impl.platform.projector.action;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class StringClientActionBean extends AbstractActionBean implements ClientAction<String> {

    private Property<String> actionName;

    private Property<String> result;

    @Override
    public Property<String> actionNameProperty() {
        return actionName;
    }

    @Override
    public Property<String> resultProperty() {
        return result;
    }
}
