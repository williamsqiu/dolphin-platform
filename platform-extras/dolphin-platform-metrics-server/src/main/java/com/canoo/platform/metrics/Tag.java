package com.canoo.platform.metrics;

import java.io.Serializable;

public interface Tag extends Serializable {

    String getKey();

    String getValue();

}
