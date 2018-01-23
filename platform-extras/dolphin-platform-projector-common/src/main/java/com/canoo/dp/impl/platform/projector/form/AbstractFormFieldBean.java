package com.canoo.dp.impl.platform.projector.form;

import com.canoo.dp.impl.platform.projector.base.Icon;
import com.canoo.dp.impl.platform.projector.message.Message;
import com.canoo.dp.impl.platform.projector.metadata.KeyValue;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public abstract class AbstractFormFieldBean<T> implements FormField<T> {

    private Property<Boolean> mandatory;

    private Property<Boolean> disabled;

    private Property<Boolean> editable;

    private ObservableList<Message> messages;

    private Property<String> description;

    private Property<Icon> icon;

    private ObservableList<KeyValue> layoutMetadata;

    private Property<String> title;


    @Override
    public Property<Boolean> mandatoryProperty() {
        return mandatory;
    }

    @Override
    public Property<Boolean> disabledProperty() {
        return disabled;
    }

    @Override
    public Property<Boolean> editableProperty() {
        return editable;
    }

    @Override
    public ObservableList<Message> getMessages() {
        return messages;
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
