package com.canoo.dp.impl.platform.projector.lazy;

import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public abstract class AbstractLazyListBean<E extends LazyListElement> implements LazyList<E> {

    private Property<Integer> listLenght;

    private ObservableList<Integer> neededContent;

    private ObservableList<KeyValue> layoutMetadata;

    private Property<Boolean> multiSelect;

    @Override
    public Property<Integer> listLengthProperty() {
        return listLenght;
    }

    @Override
    public ObservableList<Integer> getNeededContent() {
        return neededContent;
    }

    @Override
    public ObservableList<KeyValue> getLayoutMetadata() {
        return layoutMetadata;
    }

    @Override
    public Property<Boolean> multiSelectProperty() {
        return multiSelect;
    }

}
