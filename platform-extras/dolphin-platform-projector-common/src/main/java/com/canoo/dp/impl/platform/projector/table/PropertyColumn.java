package com.canoo.dp.impl.platform.projector.table;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface PropertyColumn extends Column {

    Property<String> propertyNameProperty();

    default String getPropertyName() {
        return propertyNameProperty().get();
    }

    default void setPropertyName(String propertyName) {
        propertyNameProperty().set(propertyName);
    }

}
