package com.canoo.dp.impl.platform.projector.base;

import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface WithMultiSelection<T> extends WithSelection<T> {

    ObservableList<T> getSelectedValues();

    Property<Boolean> multiSelectProperty();

    default boolean isMultiSelect() {
        Boolean multiSelect = multiSelectProperty().get();
        if (multiSelect == null) {
            return false;
        }
        return multiSelect.booleanValue();
    }

    default void setMultiSelect(boolean multiSelect) {
        multiSelectProperty().set(multiSelect);
    }

}
