package com.canoo.dp.impl.platform.projector.table;

import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public abstract class AbstractTableBean<T> implements Table<T> {

    private ObservableList<Column> columns;

    private Property<Boolean> editable;

    private Property<Boolean> sortable;

    private ObservableList<KeyValue> layoutMetadata;

    private Property<Boolean> multiSelect;

    @Override
    public ObservableList<Column> getColumns() {
        return columns;
    }

    @Override
    public Property<Boolean> editableProperty() {
        return editable;
    }

    @Override
    public Property<Boolean> sortableProperty() {
        return sortable;
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
