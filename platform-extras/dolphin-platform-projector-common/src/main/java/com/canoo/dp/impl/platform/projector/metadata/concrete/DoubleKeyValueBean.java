package com.canoo.dp.impl.platform.projector.metadata.concrete;

import com.canoo.dp.impl.platform.projector.metadata.AbstractKeyValueBean;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class DoubleKeyValueBean extends AbstractKeyValueBean<Double> {

    private Property<Double> value;

    @Override
    public Property<Double> valueProperty() {
        return value;
    }
}
