package com.canoo.impl.server.event;

import com.canoo.platform.server.spi.PlatformConfiguration;

import java.io.Serializable;

public class HazelcastConfig implements Serializable {

    private final PlatformConfiguration configuration;

    public HazelcastConfig(PlatformConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getServerName() {
        return configuration.getProperty(DistributedEventBusConfigProvider.HAZELCAST_SERVER_NAME);
    }

    public String getServerPort() {
        return configuration.getProperty(DistributedEventBusConfigProvider.HAZELCAST_SERVER_PORT);
    }

    public String getGroupName() {
        return configuration.getProperty(DistributedEventBusConfigProvider.HAZELCAST_GROUP_NAME);
    }

}
