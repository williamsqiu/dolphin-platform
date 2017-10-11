package com.canoo.platform.client;

import com.canoo.platform.core.PlatformConfiguration;

public interface ClientConfiguration extends PlatformConfiguration {

    <T> T getObjectProperty(final String key, final T defaultValue);

}
