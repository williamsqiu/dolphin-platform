package com.canoo.platform.metrics;

import com.canoo.platform.core.context.Context;
import com.canoo.platform.metrics.types.Counter;
import com.canoo.platform.metrics.types.Gauge;
import com.canoo.platform.metrics.types.Timer;

public interface Metrics {

    Counter getOrCreateCounter(String name, Context... context);

    Timer getOrCreateTimer(String name, Context... context);

    Gauge getOrCreateGauge(String name, Context... context);

}
