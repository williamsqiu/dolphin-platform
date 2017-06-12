package com.canoo.impl.server;

import com.canoo.platform.server.spi.ConfigurationProviderAdapter;

import java.util.Collections;
import java.util.Map;

public class AdditionalTestConfigurationProvider extends ConfigurationProviderAdapter {

    public final static String PROPERTY_NAME = "someTestProperty";

    public final static String PROPERTY_VALUE = "Some Value";

    @Override
    public Map<String, String> getStringProperties() {
        return Collections.singletonMap(PROPERTY_NAME, PROPERTY_VALUE);
    }
}
