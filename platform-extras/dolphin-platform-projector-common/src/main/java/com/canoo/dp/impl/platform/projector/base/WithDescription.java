package com.canoo.dp.impl.platform.projector.base;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface WithDescription {

    Property<String> descriptionProperty();

    default String getDescription() {
        return descriptionProperty().get();
    }

    default void setDescription(String description) {
        descriptionProperty().set(description);
    }

}
