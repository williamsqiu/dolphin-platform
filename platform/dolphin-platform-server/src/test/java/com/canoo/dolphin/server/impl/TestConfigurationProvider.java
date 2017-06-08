package com.canoo.dolphin.server.impl;

import com.canoo.impl.server.config.ConfigurationProvider;
import com.canoo.impl.server.config.PlatformConfiguration;

import java.util.HashMap;
import java.util.Map;

public class TestConfigurationProvider implements ConfigurationProvider {

    public final static String PROPERTY_1_NAME = "testProperty1";

    public final static String PROPERTY_2_NAME = "testProperty2";

    public final static String PROPERTY_3_NAME = "testProperty3";

    public final static String PROPERTY_1_VALUE = "YEAH!";

    public final static String PROPERTY_2_VALUE = "JUHU";

    public final static String PROPERTY_3_VALUE = null;

    @Override
    public Map<String, String> getProperties() {
        Map<String, String> ret = new HashMap<>();
        ret.put(PROPERTY_1_NAME, PROPERTY_1_VALUE);
        ret.put(PROPERTY_2_NAME, PROPERTY_2_VALUE);
        ret.put(PROPERTY_3_NAME, PROPERTY_3_VALUE);

        //This should not be overwritten from dolphin.properties
        ret.put(PlatformConfiguration.USE_CROSS_SITE_ORIGIN_FILTER, "true");
        return ret;
    }
}
