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
import com.canoo.dolphin.util.Assert;
import com.canoo.impl.server.config.PlatformConfiguration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * This class defines the configuration of the Dolphin Platform. Normally the configuration is created based
 * on defaults and a property file (see {@link com.canoo.impl.server.config.ConfigurationFileLoader}).
 */
public class RemotingConfiguration implements Serializable {

    public static final String OPEN_DOLPHIN_LOG_LEVEL = "openDolphinLogLevel";

    public static final String DOLPHIN_PLATFORM_SERVLET_MAPPING = "servletMapping";

    public static final String USE_SESSION_INVALIDATION_SERVLET = "useSessionInvalidationServlet";

    public static final String GARBAGE_COLLECTION_ACTIVE = "garbageCollectionActive";

    public static final String MAX_CLIENTS_PER_SESSION = "maxClientsPerSession";

    public static final String ID_FILTER_URL_MAPPINGS = "idFilterUrlMappings";

    public static final String MAX_POLL_TIME = "maxPollTime";

    public static final String EVENTBUS_TYPE = "eventbusType";

    private final static String USE_SESSION_INVALIDATION_SERVLET_DEFAULT_VALUE = "false";

    private final static String DOLPHIN_PLATFORM_SERVLET_MAPPING_DEFAULT_VALUE = "/dolphin";

    private final static String EVENTBUS_TYPE_DEFAULT_VALUE = DefaultEventBusProvider.DEFAULT_EVENTBUS_NAME;

    private final static String ID_FILTER_URL_MAPPINGS_DEFAULT_VALUE = "/dolphin";

    private final static String OPEN_DOLPHIN_LOG_LEVEL_DEFAULT_VALUE = "SEVERE";

    private final static String MAX_POLL_TIME_DEFAULT_VALUE = "5000";

    private final static String MAX_CLIENTS_PER_SESSION_DEFAULT_VALUE = "10";

    private final static String USE_GC_DEFAULT_VALUE = "true";

    private final PlatformConfiguration configuration;

    public RemotingConfiguration() {
        this(new PlatformConfiguration());
        configuration.setProperty(USE_SESSION_INVALIDATION_SERVLET, USE_SESSION_INVALIDATION_SERVLET_DEFAULT_VALUE);
        configuration.setProperty(DOLPHIN_PLATFORM_SERVLET_MAPPING, DOLPHIN_PLATFORM_SERVLET_MAPPING_DEFAULT_VALUE);
        configuration.setProperty(EVENTBUS_TYPE, EVENTBUS_TYPE_DEFAULT_VALUE);
        configuration.setProperty(ID_FILTER_URL_MAPPINGS, ID_FILTER_URL_MAPPINGS_DEFAULT_VALUE);
        configuration.setProperty(OPEN_DOLPHIN_LOG_LEVEL, OPEN_DOLPHIN_LOG_LEVEL_DEFAULT_VALUE);
        configuration.setProperty(MAX_POLL_TIME, MAX_POLL_TIME_DEFAULT_VALUE);
        configuration.setProperty(MAX_CLIENTS_PER_SESSION, MAX_CLIENTS_PER_SESSION_DEFAULT_VALUE);
        configuration.setProperty(GARBAGE_COLLECTION_ACTIVE, USE_GC_DEFAULT_VALUE);
    }

    public RemotingConfiguration(final PlatformConfiguration configuration) {
        this.configuration = Assert.requireNonNull(configuration, "configuration");
    }

    public String getDolphinPlatformServletMapping() {
        return configuration.getProperty(DOLPHIN_PLATFORM_SERVLET_MAPPING, DOLPHIN_PLATFORM_SERVLET_MAPPING_DEFAULT_VALUE);
    }

    public Level getOpenDolphinLogLevel() {
        String level = configuration.getProperty(OPEN_DOLPHIN_LOG_LEVEL, OPEN_DOLPHIN_LOG_LEVEL_DEFAULT_VALUE);
        if ("info".equalsIgnoreCase(level.trim())) {
            return Level.INFO;
        } else if ("severe".equalsIgnoreCase(level.trim())) {
            return Level.SEVERE;
        } else if ("all".equalsIgnoreCase(level.trim())) {
            return Level.ALL;
        } else if ("config".equalsIgnoreCase(level.trim())) {
            return Level.CONFIG;
        } else if ("fine".equalsIgnoreCase(level.trim())) {
            return Level.FINE;
        } else if ("finer".equalsIgnoreCase(level.trim())) {
            return Level.FINER;
        } else if ("finest".equalsIgnoreCase(level.trim())) {
            return Level.FINEST;
        } else if ("off".equalsIgnoreCase(level.trim())) {
            return Level.OFF;
        } else if ("warning".equalsIgnoreCase(level.trim())) {
            return Level.WARNING;
        }
        return Level.SEVERE;
    }

    public int getMaxClientsPerSession() {
        return Integer.parseInt(configuration.getProperty(MAX_CLIENTS_PER_SESSION, MAX_CLIENTS_PER_SESSION_DEFAULT_VALUE));
    }

    public boolean isUseSessionInvalidationServlet() {
        return Boolean.parseBoolean(configuration.getProperty(USE_SESSION_INVALIDATION_SERVLET, USE_SESSION_INVALIDATION_SERVLET_DEFAULT_VALUE));
    }

    public List<String> getIdFilterUrlMappings() {
        return Arrays.asList(configuration.getProperty(ID_FILTER_URL_MAPPINGS, ID_FILTER_URL_MAPPINGS_DEFAULT_VALUE).split(","));
    }

    public long getMaxPollTime() {
        return Long.parseLong(configuration.getProperty(MAX_POLL_TIME, MAX_POLL_TIME_DEFAULT_VALUE));
    }

    public boolean isUseGc() {
        return Boolean.parseBoolean(configuration.getProperty(GARBAGE_COLLECTION_ACTIVE, USE_GC_DEFAULT_VALUE));
    }

    public String getEventbusType() {
        return configuration.getProperty(EVENTBUS_TYPE, EVENTBUS_TYPE_DEFAULT_VALUE);
    }

    @Deprecated
    public PlatformConfiguration getConfiguration() {
        return configuration;
    }

    @Deprecated
    public void log() {
        getConfiguration().log();
    }

    @Deprecated
    public String getRootPackageForClasspathScan() {
        return getConfiguration().getRootPackageForClasspathScan();
    }

    @Deprecated
    public boolean isMBeanRegistration() {
        return getConfiguration().isMBeanRegistration();
    }

    @Deprecated
    public boolean isUseCrossSiteOriginFilter() {
        return getConfiguration().isUseCrossSiteOriginFilter();
    }
}

