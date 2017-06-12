package com.canoo.platform.server.spi;

import java.util.List;
import java.util.Map;

public interface ConfigurationProvider {

    Map<String, String> getStringProperties();

    Map<String, List<String>> getListProperties();

    Map<String, Boolean> getBooleanProperties();

    Map<String, Integer> getIntegerProperties();

    Map<String, Long> getLongProperties();

}
