package com.canoo.dolphin.server.event;

import com.canoo.impl.server.config.PlatformConfiguration;

import java.io.Serializable;

import static com.canoo.dolphin.server.event.DistributedEventBusConfigProvider.*;

/**
 * Created by hendrikebbers on 06.06.17.
 */
public class HazelcastConfig implements Serializable {

    private final PlatformConfiguration configuration;

    public HazelcastConfig(PlatformConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getServerName() {
        return configuration.getProperty(HAZELCAST_SERVER_NAME);
    }

    public String getServerPort() {
        return configuration.getProperty(HAZELCAST_SERVER_PORT);
    }

    public String getGroupName() {
        return configuration.getProperty(HAZELCAST_GROUP_NAME);
    }

}
