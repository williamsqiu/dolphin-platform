package com.canoo.dp.impl.platform.server.metrics.noop;

import com.canoo.dp.impl.platform.server.metrics.AbstractMeter;
import com.canoo.platform.metrics.MeterTag;
import com.canoo.platform.metrics.types.Gauge;

import java.util.List;

public class NoopGauge extends AbstractMeter implements Gauge {

    public NoopGauge(final String name, final List<MeterTag> tags) {
        super(name, tags);
    }

    @Override
    public void setValue(final double value) {

    }
}
