package com.canoo.platform.metrics.types;

import com.canoo.platform.metrics.Meter;

public interface Gauge extends Meter {

    void setValue(double value);

}
