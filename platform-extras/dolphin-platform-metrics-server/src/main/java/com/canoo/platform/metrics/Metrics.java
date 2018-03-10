package com.canoo.platform.metrics;

import com.canoo.platform.metrics.types.Counter;
import com.canoo.platform.metrics.types.Gauge;
import com.canoo.platform.metrics.types.Timer;

public interface Metrics {

    Counter getOrCreateCounter(String name, Tag... tags);

    Timer getOrCreateTimer(String name, Tag... tags);

    Gauge getOrCreateGauge(String name, Tag... tags);

}
