package com.canoo.platform.metrics.types;

import com.canoo.platform.metrics.Meter;

public interface Counter extends Meter {

    default void increment() {
        increment(1);
    }

    void increment(long amount);

}
