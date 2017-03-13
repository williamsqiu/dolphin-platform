/*
 * Copyright 2015-2017 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dolphin.server.event;

import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public class DefaultHazelcastProvider implements HazelcastProvider {

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

            ClientConfig clientConfig = new ClientConfig();
            clientConfig.getNetworkConfig().addAddress(serverName + ":" + serverPort);
            clientConfig.getGroupConfig().setName(groupName);
            hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
        }
        return hazelcastInstance;
    }
}
