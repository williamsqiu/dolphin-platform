package com.canoo.dolphin.server.event;

import com.canoo.dolphin.server.bootstrap.DolphinPlatformBootstrap;
import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;
import com.canoo.dolphin.server.event.impl.EventBusProvider;
import com.canoo.dolphin.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DistributedEventBusProvider implements EventBusProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DistributedEventBusProvider.class);

    public static final String DISTRIBUTED_EVENTBUS_NAME = "distributed";

    @Override
    public String getType() {
        return DISTRIBUTED_EVENTBUS_NAME;
    }

    public DolphinEventBus create(final DolphinPlatformConfiguration configuration) {
        LOG.debug("creating distributed event bus");

        HazelcastProvider hazelcastProvider = null;
        Iterator<HazelcastProvider> iterator = ServiceLoader.load(HazelcastProvider.class).iterator();

        //TODO: configurable
        if(iterator.hasNext()) {
            hazelcastProvider = iterator.next();
        }
        if(hazelcastProvider == null) {
            LOG.error("No hazelcast provider found!");
        }
        Assert.requireNonNull(hazelcastProvider, "hazelcastProvider");

        LOG.error("Using Hazelcast provider {}", hazelcastProvider.getClass());

        return new DistributedEventBus(hazelcastProvider.getHazelcastInstance(configuration), DolphinPlatformBootstrap.getSessionProvider(), DolphinPlatformBootstrap.getSessionLifecycleHandler());
    }

}
