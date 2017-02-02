package com.canoo.dolphin.server.event.impl;

import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;
import com.canoo.dolphin.server.event.DolphinEventBus;

public interface EventBusProvider {

    String getType();

    DolphinEventBus create(DolphinPlatformConfiguration configuration);

}
