package com.canoo.dp.impl.platform.projector.base;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface WithTitle {

    Property<String> titleProperty();

    default String getTitle() {
        return titleProperty().get();
    }

    default void setTitle(String title) {
        titleProperty().set(title);
    }

}
