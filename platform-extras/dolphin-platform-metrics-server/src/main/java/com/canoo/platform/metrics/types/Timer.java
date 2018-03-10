package com.canoo.platform.metrics.types;

import com.canoo.platform.metrics.Metric;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public interface Timer extends Metric {

    void record(long amount, TimeUnit unit);

    default void record(Duration duration) {
        record(duration.toNanos(), TimeUnit.NANOSECONDS);
    }

    void record(Runnable runnable);
}
