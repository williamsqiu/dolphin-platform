package com.canoo.dp.impl.platform.projector.metadata.concrete;

import com.canoo.dp.impl.platform.projector.metadata.AbstractKeyValueBean;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class LongKeyValueBean extends AbstractKeyValueBean<Long> {

    private Property<Long> value;

    @Override
    public Property<Long> valueProperty() {
        return value;
    }
}
