package com.canoo.dp.impl.server.bootstrap;

import com.canoo.platform.server.spi.ConfigurationProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleConfigurationProvider implements ConfigurationProvider {

    private final Map<String, String> stringProperties = new HashMap<>();

    private final Map<String, List<String>> listProperties = new HashMap<>();

    private final Map<String, Boolean> booleanProperties = new HashMap<>();

    private final Map<String, Integer> intProperties = new HashMap<>();

    private final Map<String, Long> longProperties = new HashMap<>();

    protected final void addString(String key, String value) {
        stringProperties.put(key, value);
    }

    protected final void addList(String key, List<String> value) {
        listProperties.put(key, value);
    }

    protected final void addBoolean(String key, boolean value) {
        booleanProperties.put(key, value);
    }

    protected final void addInt(String key, int value) {
        intProperties.put(key, value);
    }

    protected final void addLong(String key, long value) {
        longProperties.put(key, value);
    }

    @Override
    public final Map<String, String> getStringProperties() {
        return Collections.unmodifiableMap(stringProperties);
    }

    @Override
    public final Map<String, List<String>> getListProperties() {
        return Collections.unmodifiableMap(listProperties);
    }

    @Override
    public final Map<String, Boolean> getBooleanProperties() {
        return Collections.unmodifiableMap(booleanProperties);
    }

    @Override
    public final Map<String, Integer> getIntegerProperties() {
        return Collections.unmodifiableMap(intProperties);
    }

    @Override
    public final Map<String, Long> getLongProperties() {
        return Collections.unmodifiableMap(longProperties);
    }
}
