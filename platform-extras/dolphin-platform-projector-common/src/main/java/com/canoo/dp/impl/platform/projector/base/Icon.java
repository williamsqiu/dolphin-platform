package com.canoo.dp.impl.platform.projector.base;


import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface Icon extends Projectable {

    Property<String> iconFamilyProperty();

    Property<String> iconCodeProperty();

    default String getIconFamily() {
        return iconFamilyProperty().get();
    }

    default String getIconCode() {
        return iconCodeProperty().get();
    }

    default void setIconFamily(String iconFamily) {
        iconFamilyProperty().set(iconFamily);
    }

    default void setIconCode(String iconCode) {
        iconCodeProperty().set(iconCode);
    }
}
