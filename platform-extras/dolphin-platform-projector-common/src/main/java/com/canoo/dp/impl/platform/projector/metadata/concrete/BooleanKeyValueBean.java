package com.canoo.dp.impl.platform.projector.metadata.concrete;

import com.canoo.dp.impl.platform.projector.metadata.AbstractKeyValueBean;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class BooleanKeyValueBean extends AbstractKeyValueBean<Boolean> {

    private Property<Boolean> value;

    @Override
    public Property<Boolean> valueProperty() {
        return value;
    }
}
