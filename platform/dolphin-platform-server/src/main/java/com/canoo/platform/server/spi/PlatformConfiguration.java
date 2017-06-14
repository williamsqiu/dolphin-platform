package com.canoo.platform.server.spi;

import java.io.Serializable;
import java.util.*;

public interface PlatformConfiguration extends Serializable {

    boolean containsProperty(final String key);

    String getProperty(final String key);

    boolean getBooleanProperty(final String key, boolean defaultValue);

    boolean getBooleanProperty(final String key);

    int getIntProperty(final String key, int defaultValue);

    long getLongProperty(final String key, long defaultValue);

    List<String> getListProperty(final String key);

    List<String> getListProperty(final String key, final List<String> defaultValues);

    String getProperty(final String key, final String defaultValue);

    Set<String> getPropertyKeys();
}
