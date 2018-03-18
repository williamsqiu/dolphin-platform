package com.canoo.dp.impl.platform.server.metrics.noop;

import com.canoo.dp.impl.platform.server.metrics.AbstractMeter;
import com.canoo.dp.impl.platform.server.metrics.MeterIdentifier;
import com.canoo.platform.metrics.types.Timer;

import java.util.concurrent.TimeUnit;

public class NoopTimer extends AbstractMeter implements Timer {

    public NoopTimer(final MeterIdentifier identifier, final AutoCloseable closeable) {
        super(identifier, closeable);
    }

    @Override
    public void record(final long amount, final TimeUnit unit) {

    }
}
