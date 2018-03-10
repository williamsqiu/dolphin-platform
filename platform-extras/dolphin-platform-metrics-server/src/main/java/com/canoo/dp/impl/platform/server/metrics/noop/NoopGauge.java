package com.canoo.dp.impl.platform.server.metrics.noop;

import com.canoo.dp.impl.platform.server.metrics.AbstractMeter;
import com.canoo.dp.impl.platform.server.metrics.MeterIdentifier;
import com.canoo.platform.metrics.types.Gauge;

public class NoopGauge extends AbstractMeter implements Gauge {

    public NoopGauge(final MeterIdentifier identifier, AutoCloseable closeable) {
        super(identifier, closeable);
    }

    @Override
    public void setValue(final double value) {

    }
}
