package com.canoo.dp.impl.platform.projector.action;


import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface ActionQueue extends Action {

    ObservableList<Action> getActions();

}
