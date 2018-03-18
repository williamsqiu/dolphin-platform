package com.canoo.platform.metrics.types;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.metrics.Metric;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public interface Timer extends Metric {

    void record(long amount, TimeUnit unit);

    default void record(final Duration duration) {
        Assert.requireNonNull(duration, "duration");
        record(duration.toNanos(), TimeUnit.NANOSECONDS);
    }
}
