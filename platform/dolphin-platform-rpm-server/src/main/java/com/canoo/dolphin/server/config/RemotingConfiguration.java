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
package com.canoo.dolphin.server.config;

import com.canoo.dolphin.server.event.impl.DefaultEventBusProvider;
import com.canoo.impl.platform.core.Assert;
import com.canoo.impl.server.config.PlatformConfiguration;

import java.io.Serializable;

/**
 * This class defines the configuration of the Dolphin Platform. Normally the configuration is created based
 * on defaults and a property file (see {@link com.canoo.impl.server.config.ConfigurationFileLoader}).
 */
public class RemotingConfiguration implements Serializable {

    public static final String ACTIVE = "remoting.active";

    public static final String DOLPHIN_PLATFORM_SERVLET_MAPPING = "servletMapping";

    public static final String GARBAGE_COLLECTION_ACTIVE = "garbageCollectionActive";

    public static final String MAX_POLL_TIME = "maxPollTime";

    public static final String EVENTBUS_TYPE = "eventbusType";

    public static final boolean ACTIVE_DEFAULT_VALUE = true;

    private final static String DOLPHIN_PLATFORM_SERVLET_MAPPING_DEFAULT_VALUE = "/dolphin";

    private final static String EVENTBUS_TYPE_DEFAULT_VALUE = DefaultEventBusProvider.DEFAULT_EVENTBUS_NAME;

    private final static String OPEN_DOLPHIN_LOG_LEVEL_DEFAULT_VALUE = "SEVERE";

    private final static long MAX_POLL_TIME_DEFAULT_VALUE = 5000;

    private final static boolean USE_GC_DEFAULT_VALUE = true;

    private final PlatformConfiguration configuration;

    public RemotingConfiguration() {
        this(new PlatformConfiguration());
        configuration.setProperty(DOLPHIN_PLATFORM_SERVLET_MAPPING, DOLPHIN_PLATFORM_SERVLET_MAPPING_DEFAULT_VALUE);
        configuration.setProperty(EVENTBUS_TYPE, EVENTBUS_TYPE_DEFAULT_VALUE);
        configuration.setLongProperty(MAX_POLL_TIME, MAX_POLL_TIME_DEFAULT_VALUE);
        configuration.setBooleanProperty(GARBAGE_COLLECTION_ACTIVE, USE_GC_DEFAULT_VALUE);
    }

    public RemotingConfiguration(final PlatformConfiguration configuration) {
        this.configuration = Assert.requireNonNull(configuration, "configuration");
    }

    public String getDolphinPlatformServletMapping() {
        return configuration.getProperty(DOLPHIN_PLATFORM_SERVLET_MAPPING, DOLPHIN_PLATFORM_SERVLET_MAPPING_DEFAULT_VALUE);
    }

    public long getMaxPollTime() {
        return configuration.getLongProperty(MAX_POLL_TIME, MAX_POLL_TIME_DEFAULT_VALUE);
    }

    public boolean isUseGc() {
        return configuration.getBooleanProperty(GARBAGE_COLLECTION_ACTIVE, USE_GC_DEFAULT_VALUE);
    }

    public String getEventbusType() {
        return configuration.getProperty(EVENTBUS_TYPE, EVENTBUS_TYPE_DEFAULT_VALUE);
    }

    public PlatformConfiguration getConfiguration() {
        return configuration;
    }

    public boolean isRemotingActive() {
        return configuration.getBooleanProperty(ACTIVE, ACTIVE_DEFAULT_VALUE);
    }
}

