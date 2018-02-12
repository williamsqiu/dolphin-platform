package com.canoo.dp.impl.platform.projector.table;

import com.canoo.dp.impl.platform.projector.action.Action;
import com.canoo.dp.impl.platform.projector.base.Icon;
import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class ActionColumnBean<T extends Action> implements ActionColumn<T> {

    private Property<T> action;

    private Property<Boolean> editable;

    private Property<Boolean> sortable;

    private Property<String> description;

    private Property<Icon> icon;

    private ObservableList<KeyValue> layoutMetadata;

    private Property<String> title;

    @Override
    public Property<T> actionProperty() {
        return action;
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
    public Property<String> descriptionProperty() {
        return description;
    }

    @Override
    public Property<Icon> iconProperty() {
        return icon;
    }

    @Override
    public ObservableList<KeyValue> getLayoutMetadata() {
        return layoutMetadata;
    }

    @Override
    public Property<String> titleProperty() {
        return title;
    }
}
