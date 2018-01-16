package com.canoo.dp.impl.validation;

import com.canoo.dp.impl.remoting.MockedProperty;
import com.canoo.platform.remoting.RemotingBean;
import com.canoo.platform.remoting.Property;

import javax.validation.constraints.Future;

@RemotingBean
class TestBeanFutureInvalidAnnotation {

    @Future
    private Property<Short> date = new MockedProperty<>();

    public Property<Short> dateProperty() {
        return date;
    }
}
