package com.canoo.dp.impl.platform.projector.base;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public interface Progress extends Projectable {

    Property<Boolean> indeterminateProperty();

    Property<Double> progressProperty();

    default boolean isIndeterminate() {
        Boolean val = indeterminateProperty().get();
        if(val == null) {
            return false;
        }
        return val.booleanValue();
    }

    default void setIndeterminate(boolean indeterminate) {
        indeterminateProperty().set(indeterminate);
    }

    default double getProgress() {
        Double val = progressProperty().get();
        if(val == null) {
            return 0.0;
        }
        return val.doubleValue();
    }

    default void setProgress(double progress) {
        progressProperty().set(progress);
    }
}
