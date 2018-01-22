package com.canoo.dp.impl.platform.projector.base;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface WithResult<T> {

    Property<T> resultProperty();

    default T getResult() {
        return resultProperty().get();
    }

    default void setResult(T result) {
        resultProperty().set(result);
    }
}
