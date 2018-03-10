package com.canoo.platform.metrics.types;

import com.canoo.platform.metrics.Metric;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface Timer extends Metric {

    void record(long amount, TimeUnit unit);

    default void record(Duration duration) {
        record(duration.toNanos(), TimeUnit.NANOSECONDS);
    }

    <T> T record(Supplier<T> supplier);

    <T> T recordCallable(Callable<T> callable) throws Exception;

    void record(Runnable runnable);

    void record(Runnable runnable, Executor executor);
}
