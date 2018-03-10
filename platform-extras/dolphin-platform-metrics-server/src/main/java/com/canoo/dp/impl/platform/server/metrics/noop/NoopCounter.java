package com.canoo.dp.impl.platform.server.metrics.noop;

import com.canoo.dp.impl.platform.server.metrics.AbstractMeter;
import com.canoo.platform.metrics.MeterTag;
import com.canoo.platform.metrics.types.Counter;

import java.util.List;

public class NoopCounter extends AbstractMeter implements Counter {

    public NoopCounter(final String name, final List<MeterTag> tags) {
        super(name, tags);
    }

    @Override
    public void increment(final long amount) {

    }

    @Override
    public void close() throws Exception {}
}
