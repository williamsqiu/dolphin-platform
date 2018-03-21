package com.canoo.dp.impl.platform.projector.metadata.concrete;

import com.canoo.dp.impl.platform.projector.metadata.AbstractKeyValueBean;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class IntegerKeyValueBean extends AbstractKeyValueBean<Integer> {

    private Property<Integer> value;

    @Override
    public Property<Integer> valueProperty() {
        return value;
    }
}
