package com.canoo.impl.server.config;

import com.canoo.platform.server.spi.ConfigurationProviderAdapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RemotingDefaultValueProvider extends ConfigurationProviderAdapter {

    @Override
    public Map<String, String> getStringProperties() {
        HashMap<String, String> ret = new HashMap<>();

        ret.put(RemotingConfiguration.DOLPHIN_PLATFORM_SERVLET_MAPPING, RemotingConfiguration.DOLPHIN_PLATFORM_SERVLET_MAPPING_DEFAULT_VALUE);
        ret.put(RemotingConfiguration.DOLPHIN_PLATFORM_INTERRUPT_SERVLET_MAPPING, RemotingConfiguration.DOLPHIN_PLATFORM_INTERRUPT_SERVLET_MAPPING_DEFAULT_VALUE);
        ret.put(RemotingConfiguration.EVENTBUS_TYPE, RemotingConfiguration.EVENTBUS_TYPE_DEFAULT_VALUE);
        return ret;
    }

    @Override
    public Map<String, Long> getLongProperties() {
        return Collections.singletonMap(RemotingConfiguration.MAX_POLL_TIME, RemotingConfiguration.MAX_POLL_TIME_DEFAULT_VALUE);
    }

    @Override
    public Map<String, Boolean> getBooleanProperties() {
        return Collections.singletonMap(RemotingConfiguration.GARBAGE_COLLECTION_ACTIVE, RemotingConfiguration.USE_GC_DEFAULT_VALUE);
    }
}
