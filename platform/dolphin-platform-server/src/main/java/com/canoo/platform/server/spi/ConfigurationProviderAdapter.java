package com.canoo.platform.server.spi;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConfigurationProviderAdapter implements ConfigurationProvider {
    @Override
    public Map<String, String> getStringProperties() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, List<String>> getListProperties() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Boolean> getBooleanProperties() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Integer> getIntegerProperties() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Long> getLongProperties() {
        return Collections.emptyMap();
    }
}
