package com.canoo.dp.impl.platform.projector.action;

import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class DefaultActionQueue extends AbstractActionBean implements ActionQueue {

    private ObservableList<Action> actions;

    @Override
    public ObservableList<Action> getActions() {
        return actions;
    }
}
