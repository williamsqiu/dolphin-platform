package com.canoo.dp.impl.platform.projector.action;

import com.canoo.dp.impl.platform.projector.base.Projectable;
import com.canoo.dp.impl.platform.projector.base.WithDescription;
import com.canoo.dp.impl.platform.projector.base.WithIcon;
import com.canoo.dp.impl.platform.projector.base.WithLayoutMetadata;
import com.canoo.dp.impl.platform.projector.base.WithTitle;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface Action extends WithTitle, WithDescription, WithIcon, WithLayoutMetadata, Projectable {

    Property<Boolean> disabledProperty();

    Property<Boolean> blockUiProperty();

    Property<Boolean> blockOnActionProperty();

    default boolean isDisabled() {
        Boolean val = disabledProperty().get();
        if(val == null) {
            return false;
        }
        return val.booleanValue();
    }

    default boolean isBlockUi() {
        Boolean val = blockUiProperty().get();
        if(val == null) {
            return false;
        }
        return val.booleanValue();
    }

    default boolean isBlockOnAction() {
        Boolean val = blockOnActionProperty().get();
        if(val == null) {
            return false;
        }
        return val.booleanValue();
    }

    default void setDisabled(boolean disabled) {
        disabledProperty().set(disabled);
    }

    default void setBlockUi(boolean blockUi) {
        blockUiProperty().set(blockUi);
    }

    default void setBlockOnAction(boolean blockOnAction) {
        blockOnActionProperty().set(blockOnAction);
    }

}
