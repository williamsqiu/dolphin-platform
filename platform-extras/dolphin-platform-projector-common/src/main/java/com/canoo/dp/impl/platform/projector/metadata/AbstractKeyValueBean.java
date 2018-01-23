package com.canoo.dp.impl.platform.projector.metadata;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public abstract class AbstractKeyValueBean<T> implements KeyValue<T> {

    private Property<String> key;

    @Override
    public Property<String> keyProperty() {
        return key;
    }

}
