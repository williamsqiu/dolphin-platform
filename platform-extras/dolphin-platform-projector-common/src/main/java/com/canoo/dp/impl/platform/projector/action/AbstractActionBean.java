package com.canoo.dp.impl.platform.projector.action;


import com.canoo.dp.impl.platform.projector.base.Icon;
import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;

public abstract class AbstractActionBean implements Action {

    private Property<Boolean> disabled;

    private Property<String> description;

    private Property<Icon> icon;

    private ObservableList<KeyValue> layoutMetadata;

    private Property<String> title;

    private Property<Boolean> blockUi;

    private Property<Boolean> blockOnAction;

    @Override
    public Property<Boolean> disabledProperty() {
        return disabled;
    }

    @Override
    public Property<Boolean> blockUiProperty() {
        return blockUi;
    }

    @Override
    public Property<Boolean> blockOnActionProperty() {
        return blockOnAction;
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
