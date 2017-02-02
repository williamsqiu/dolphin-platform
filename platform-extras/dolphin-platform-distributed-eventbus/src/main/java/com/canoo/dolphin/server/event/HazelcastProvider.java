package com.canoo.dolphin.server.event;

import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;
import com.hazelcast.core.HazelcastInstance;

public interface HazelcastProvider {

    HazelcastInstance getHazelcastInstance(DolphinPlatformConfiguration configuration);

}
