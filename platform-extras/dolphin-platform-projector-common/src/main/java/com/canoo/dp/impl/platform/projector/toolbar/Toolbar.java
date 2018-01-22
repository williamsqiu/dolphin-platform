package com.canoo.dp.impl.platform.projector.toolbar;

import com.canoo.dp.impl.platform.projector.action.Action;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface Toolbar {

    ObservableList<Action> getActions();

}
