package com.canoo.platform.metrics.types;

import com.canoo.platform.metrics.Metric;

public interface Gauge extends Metric {

    void setValue(double value);

}
