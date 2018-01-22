package com.canoo.dp.impl.platform.projector.table;

import com.canoo.dp.impl.platform.projector.base.WithDescription;
import com.canoo.dp.impl.platform.projector.base.WithIcon;
import com.canoo.dp.impl.platform.projector.base.WithLayoutMetadata;
import com.canoo.dp.impl.platform.projector.base.WithTitle;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface Column extends WithTitle, WithDescription, WithIcon, WithLayoutMetadata {

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
