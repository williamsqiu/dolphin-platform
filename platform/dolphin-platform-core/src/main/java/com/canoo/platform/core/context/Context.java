package com.canoo.platform.core.context;

import java.io.Serializable;

public interface Context extends Serializable {

    String getType();

    String getValue();

}
