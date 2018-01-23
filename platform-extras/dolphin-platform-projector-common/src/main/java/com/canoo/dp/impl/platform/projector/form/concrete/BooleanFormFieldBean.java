package com.canoo.dp.impl.platform.projector.form.concrete;

import com.canoo.dp.impl.platform.projector.form.AbstractFormFieldBean;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class BooleanFormFieldBean extends AbstractFormFieldBean<Boolean> {

    private Property<Class> contentType;

    private Property<Boolean> value;

    @Override
    public Property<Class> contentTypeProperty() {
        return contentType;
    }

    @Override
    public Property<Boolean> valueProperty() {
        return value;
    }
}
