package com.canoo.dp.impl.platform.projector.form;

import com.canoo.dp.impl.platform.projector.base.WithDescription;
import com.canoo.dp.impl.platform.projector.base.WithIcon;
import com.canoo.dp.impl.platform.projector.base.WithLayoutMetadata;
import com.canoo.dp.impl.platform.projector.base.WithTitle;
import com.canoo.dp.impl.platform.projector.message.Message;
import com.canoo.platform.remoting.ObservableList;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface FormField<T> extends WithTitle, WithDescription, WithIcon, WithLayoutMetadata {

    Property<Boolean> mandatoryProperty();

    Property<Boolean> disabledProperty();

    Property<Boolean> editableProperty();

    Property<Class> contentTypeProperty();

    Property<T> valueProperty();

    ObservableList<Message> getMessages();

    default boolean isMandatory() {
        Boolean mandatory = mandatoryProperty().get();
        if(mandatory == null) {
            return false;
        }
        return mandatory.booleanValue();
    }

    default boolean isDisabled() {
        Boolean disabled = disabledProperty().get();
        if(disabled == null) {
            return false;
        }
        return disabled.booleanValue();
    }

    default boolean isEditable() {
        Boolean val = editableProperty().get();
        if(val == null) {
            return false;
        }
        return val.booleanValue();
    }

    default T getValue() {
        return valueProperty().get();
    }

    default Class<T> getContentType() {
        return contentTypeProperty().get();
    }

    default void setMandatory(boolean mandatory) {
        mandatoryProperty().set(mandatory);
    }

    default void setDisabled(boolean disabled) {
        disabledProperty().set(disabled);
    }

    default void setEditable(boolean editable) {
        editableProperty().set(editable);
    }

    default void setValue(T value) {
        valueProperty().set(value);
    }

    default void setContentType(Class<T> contentType) {
        contentTypeProperty().set(contentType);
    }

}
