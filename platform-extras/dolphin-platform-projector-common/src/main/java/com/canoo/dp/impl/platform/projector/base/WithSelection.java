package com.canoo.dp.impl.platform.projector.base;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface WithSelection<T> {

    Property<T> selectedValueProperty();

    default T getSelectedValue() {
        return selectedValueProperty().get();
    }

    default void setSelectedValue(T value) {
        selectedValueProperty().set(value);
    }

}
