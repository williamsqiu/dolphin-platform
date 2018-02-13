package com.canoo.platform.core.timing;

import java.io.Serializable;
import java.util.List;

public interface TimingGroup extends Serializable {

    String getName();

    default String getDescription() {
        return null;
    }

    List<TimingContext> getContent();
}
