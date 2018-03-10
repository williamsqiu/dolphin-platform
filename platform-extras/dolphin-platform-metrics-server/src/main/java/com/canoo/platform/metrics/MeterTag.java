package com.canoo.platform.metrics;

import java.io.Serializable;

public interface MeterTag extends Serializable {

    String getKey();

    String getValue();

}
