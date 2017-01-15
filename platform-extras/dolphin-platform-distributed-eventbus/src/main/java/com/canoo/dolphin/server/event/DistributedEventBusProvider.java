package com.canoo.dolphin.server.event;

import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;
import com.canoo.dolphin.server.context.DolphinSessionLifecycleHandler;
import com.canoo.dolphin.server.context.DolphinSessionProvider;

public class DistributedEventBusProvider {

    private final HazelcastProvider hazelcastProvider;

    private final DolphinSessionProvider sessionProvider;

    private final DolphinSessionLifecycleHandler lifecycleHandler;

    public DistributedEventBusProvider(HazelcastProvider hazelcastProvider, DolphinSessionProvider sessionProvider, DolphinSessionLifecycleHandler lifecycleHandler) {
        this.hazelcastProvider = hazelcastProvider;
        this.sessionProvider = sessionProvider;
        this.lifecycleHandler = lifecycleHandler;
    }

    public DistributedEventBus create(DolphinPlatformConfiguration configuration) {
        return new DistributedEventBus(hazelcastProvider.getHazelcastInstance(configuration), sessionProvider, lifecycleHandler);
    }

}
