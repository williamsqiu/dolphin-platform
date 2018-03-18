package com.canoo.dp.impl.platform.server.metrics.noop;

import com.canoo.dp.impl.platform.server.metrics.AbstractMeter;
import com.canoo.dp.impl.platform.server.metrics.MeterIdentifier;
import com.canoo.platform.metrics.types.Counter;

public class NoopCounter extends AbstractMeter implements Counter {

    public NoopCounter(final MeterIdentifier identifier, final AutoCloseable closeable) {
        super(identifier, closeable);
    }

    @Override
    public void increment(final long amount) {}

}
