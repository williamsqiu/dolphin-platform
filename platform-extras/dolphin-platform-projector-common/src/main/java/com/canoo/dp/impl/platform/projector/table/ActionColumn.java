package com.canoo.dp.impl.platform.projector.table;

import com.canoo.dp.impl.platform.projector.action.Action;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface ActionColumn<T extends Action> extends Column {

    Property<T> actionProperty();

    default T getAction() {
        return actionProperty().get();
    }

    default void setAction(T action) {
        actionProperty().set(action);
    }

}
