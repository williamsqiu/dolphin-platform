package com.canoo.dp.impl.platform.projector.base;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface WithIcon {

    Property<Icon> iconProperty();

    default Icon getIcon() {
        return iconProperty().get();
    }

    default void setIcon(Icon icon) {
        iconProperty().set(icon);
    }

}
