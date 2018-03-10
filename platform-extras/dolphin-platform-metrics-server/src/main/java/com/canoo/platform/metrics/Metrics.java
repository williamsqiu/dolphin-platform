package com.canoo.platform.metrics;

import com.canoo.platform.metrics.types.Counter;
import com.canoo.platform.metrics.types.Gauge;
import com.canoo.platform.metrics.types.Timer;

public interface Metrics {

    Counter getOrCreateCounter(String name, MeterTag... tags);

    Timer getOrCreateTimer(String name, MeterTag... tags);

    Gauge getOrCreateGauge(String name, MeterTag... tags);

}
