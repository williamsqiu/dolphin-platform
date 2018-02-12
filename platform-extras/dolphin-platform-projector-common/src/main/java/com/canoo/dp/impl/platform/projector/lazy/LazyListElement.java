package com.canoo.dp.impl.platform.projector.lazy;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface LazyListElement {

    Property<Integer> indexProperty();

    default Integer getIndex() {
        return indexProperty().get();
    }

    default void setIndex(Integer index) {
        indexProperty().set(index);
    }
}
