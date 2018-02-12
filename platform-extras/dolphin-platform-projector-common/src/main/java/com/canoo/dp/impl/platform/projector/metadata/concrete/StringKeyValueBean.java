package com.canoo.dp.impl.platform.projector.metadata.concrete;

import com.canoo.dp.impl.platform.projector.metadata.AbstractKeyValueBean;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class StringKeyValueBean extends AbstractKeyValueBean<String> {

    private Property<String> value;

    @Override
    public Property<String> valueProperty() {
        return value;
    }
}
