package com.canoo.dolphin.server.event;

import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.canoo.dolphin.server.event.DistributedEventBusConfigProvider.HAZELCAST_GROUP_NAME;
import static com.canoo.dolphin.server.event.DistributedEventBusConfigProvider.HAZELCAST_SERVER_NAME;
import static com.canoo.dolphin.server.event.DistributedEventBusConfigProvider.HAZELCAST_SERVER_PORT;

public class DefaultHazelcastProvider implements HazelcastProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultHazelcastProvider.class);

    private HazelcastInstance hazelcastInstance;

    public synchronized HazelcastInstance getHazelcastInstance(DolphinPlatformConfiguration configuration) {
        if(hazelcastInstance == null) {
            String serverName = configuration.getProperty(HAZELCAST_SERVER_NAME);
            String serverPort = configuration.getProperty(HAZELCAST_SERVER_PORT);
            String groupName = configuration.getProperty(HAZELCAST_GROUP_NAME);

            LOG.debug("Hazelcast server name: {}", serverName);
            LOG.debug("Hazelcast server port: {}", serverPort);
            LOG.debug("Hazelcast group name: {}", groupName);

            ClientConfig clientConfig = new ClientConfig();
            clientConfig.getNetworkConfig().addAddress(serverName + ":" + serverPort);
            clientConfig.getGroupConfig().setName(groupName);
            hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
        }
        return hazelcastInstance;
    }
}
