package com.canoo.dolphin.server.event;

import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultHazelcastProvider implements HazelcastProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultHazelcastProvider.class);


    private static final String HAZELCAST_SERVER_NAME = "hazelcast.server.name";

    private static final String HAZELCAST_SERVER_PORT = "hazelcast.server.port";

    private static final String HAZELCAST_GROUP_NAME = "hazelcast.group.name";

    private static final String DEFAULT_HAZELCAST_SERVER = "localhost";

    private static final String DEFAULT_HAZELCAST_PORT = "5701";

    private static final String DEFAULT_HAZELCAST_GROUP_NAME = "micro-landscape";

    private HazelcastInstance hazelcastInstance;

    public synchronized HazelcastInstance getHazelcastInstance(DolphinPlatformConfiguration configuration) {
        if(hazelcastInstance == null) {
            String serverName = configuration.getProperty(HAZELCAST_SERVER_NAME);
            if(serverName == null) {
                serverName = DEFAULT_HAZELCAST_SERVER;
            }

            String serverPort = configuration.getProperty(HAZELCAST_SERVER_PORT);
            if(serverPort == null) {
                serverPort = DEFAULT_HAZELCAST_PORT;
            }

            String groupName = configuration.getProperty(HAZELCAST_GROUP_NAME);
            if(groupName == null) {
                groupName = DEFAULT_HAZELCAST_GROUP_NAME;
            }

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
