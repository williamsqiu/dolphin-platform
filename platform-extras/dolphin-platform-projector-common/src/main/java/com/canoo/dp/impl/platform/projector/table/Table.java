package com.canoo.dp.impl.platform.projector.table;

import com.canoo.dp.impl.platform.projector.base.Projectable;
import com.canoo.dp.impl.platform.projector.base.WithLayoutMetadata;
import com.canoo.dp.impl.platform.projector.base.WithMultiSelection;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface Table<T> extends WithMultiSelection<T>, WithLayoutMetadata, Projectable {

    ObservableList<Column> getColumns();

    ObservableList<T> getItems();

    Property<Boolean> editableProperty();

    Property<Boolean> sortableProperty();

    default boolean isEditable() {
        Boolean val = editableProperty().get();
        if(val == null) {
            return false;
        }
        return val.booleanValue();
    }

    default void setEditable(boolean editable) {
        editableProperty().set(editable);
    }

    default boolean isSortable() {
        Boolean val = sortableProperty().get();
        if(val == null) {
            return false;
        }
        return val.booleanValue();
    }

    default void setSortable(boolean sortable) {
        sortableProperty().set(sortable);
    }
}
