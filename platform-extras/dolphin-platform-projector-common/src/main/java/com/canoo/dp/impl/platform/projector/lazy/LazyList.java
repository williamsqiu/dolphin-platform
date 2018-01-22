package com.canoo.dp.impl.platform.projector.lazy;

import com.canoo.dp.impl.platform.projector.base.Projectable;
import com.canoo.dp.impl.platform.projector.base.WithLayoutMetadata;
import com.canoo.dp.impl.platform.projector.base.WithMultiSelection;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface LazyList<E extends LazyListElement> extends WithMultiSelection<E>, WithLayoutMetadata, Projectable {

    Property<Integer> listLengthProperty();

    ObservableList<E> getLoadedContent();

    ObservableList<Integer> getNeededContent();

    default Integer getListLength() {
        return listLengthProperty().get();
    }

    default void setListLength(Integer listLength) {
        listLengthProperty().set(listLength);
    }

}
