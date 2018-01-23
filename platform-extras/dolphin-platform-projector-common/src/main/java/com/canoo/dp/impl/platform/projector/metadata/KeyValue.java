package com.canoo.dp.impl.platform.projector.metadata;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface KeyValue<T> {

    Property<String> keyProperty();

    Property<T> valueProperty();

    default void setKey(String key) {
        keyProperty().set(key);
    }

    default void setValue(T value) {
        valueProperty().set(value);
    }

    default String getKey() {
        return keyProperty().get();
    }

    default T getValue() {
        return valueProperty().get();
    }
}
