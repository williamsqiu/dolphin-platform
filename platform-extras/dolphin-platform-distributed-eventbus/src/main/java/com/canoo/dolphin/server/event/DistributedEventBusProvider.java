package com.canoo.dolphin.server.event;

import com.canoo.dolphin.server.bootstrap.DolphinPlatformBootstrap;
import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;
import com.canoo.dolphin.server.event.impl.EventBusProvider;
import com.canoo.dolphin.util.Assert;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DistributedEventBusProvider implements EventBusProvider {

    public static final String DISTRIBUTED_EVENTBUS_NAME = "distributed";

    @Override
    public String getType() {
        return DISTRIBUTED_EVENTBUS_NAME;
    }

    public DolphinEventBus create(final DolphinPlatformConfiguration configuration) {
        HazelcastProvider hazelcastProvider = null;
        Iterator<HazelcastProvider> iterator = ServiceLoader.load(HazelcastProvider.class).iterator();

        //TODO: configurable
        if(iterator.hasNext()) {
            hazelcastProvider = iterator.next();
        }
        Assert.requireNonNull(hazelcastProvider, "hazelcastProvider");

        return new DistributedEventBus(hazelcastProvider.getHazelcastInstance(configuration), DolphinPlatformBootstrap.getSessionProvider(), DolphinPlatformBootstrap.getSessionLifecycleHandler());
    }

}
