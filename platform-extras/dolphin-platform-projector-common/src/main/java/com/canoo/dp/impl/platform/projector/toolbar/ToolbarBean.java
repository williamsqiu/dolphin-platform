package com.canoo.dp.impl.platform.projector.toolbar;

import com.canoo.dp.impl.platform.projector.action.Action;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class ToolbarBean implements Toolbar {

    private ObservableList<Action> actions;

    @Override
    public ObservableList<Action> getActions() {
        return actions;
    }
}
