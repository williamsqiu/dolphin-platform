package com.canoo.platform.metrics.types;

import com.canoo.platform.metrics.Metric;

public interface Counter extends Metric {

    default void increment() {
        increment(1);
    }

    void increment(long amount);

}
