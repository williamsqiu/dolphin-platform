package com.canoo.dp.impl.platform.server.metrics.noop;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.server.metrics.AbstractMeter;
import com.canoo.dp.impl.platform.server.metrics.MeterIdentifier;
import com.canoo.platform.metrics.types.Timer;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class NoopTimer extends AbstractMeter implements Timer {

    public NoopTimer(final MeterIdentifier identifier, AutoCloseable closeable) {
        super(identifier, closeable);
    }

    @Override
    public void record(final long amount, final TimeUnit unit) {

    }

    @Override
    public <T> T record(final Supplier<T> supplier) {
        Assert.requireNonNull(supplier, "supplier");
        return supplier.get();
    }

    @Override
    public <T> T recordCallable(final Callable<T> callable) throws Exception {
        Assert.requireNonNull(callable, "callable");
        return callable.call();
    }

    @Override
    public void record(final Runnable runnable) {
        Assert.requireNonNull(runnable, "runnable");
        runnable.run();
    }

    @Override
    public void record(final Runnable runnable, final Executor executor) {
        Assert.requireNonNull(runnable, "runnable");
        Assert.requireNonNull(executor, "executor");
        executor.execute(runnable);
    }
}
