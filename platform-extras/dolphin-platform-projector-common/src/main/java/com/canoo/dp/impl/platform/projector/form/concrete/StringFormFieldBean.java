package com.canoo.dp.impl.platform.projector.form.concrete;

import com.canoo.dp.impl.platform.projector.form.AbstractFormFieldBean;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class StringFormFieldBean extends AbstractFormFieldBean<String> {

    private Property<Class> contentType;

    private Property<String> value;

    @Override
    public Property<Class> contentTypeProperty() {
        return contentType;
    }

    @Override
    public Property<String> valueProperty() {
        return value;
    }
}
