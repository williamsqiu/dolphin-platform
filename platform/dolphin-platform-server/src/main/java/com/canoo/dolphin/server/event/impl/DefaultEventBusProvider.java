package com.canoo.dolphin.server.event.impl;

import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;
import com.canoo.dolphin.server.event.DolphinEventBus;

public class DefaultEventBusProvider implements EventBusProvider {

    public static final String DEFAULT_EVENTBUS_NAME = "default";

    @Override
    public String getType() {
        return DEFAULT_EVENTBUS_NAME;
    }

    @Override
    public DolphinEventBus create(DolphinPlatformConfiguration configuration) {
        return new DefaultDolphinEventBus();
    }
}
