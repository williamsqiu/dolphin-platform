package com.canoo.dp.impl.platform.projector.base;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class IconBean implements Icon {

    private Property<String> iconFamily;

    private Property<String> iconCode;

    @Override
    public Property<String> iconFamilyProperty() {
        return iconFamily;
    }

    @Override
    public Property<String> iconCodeProperty() {
        return iconCode;
    }
}
