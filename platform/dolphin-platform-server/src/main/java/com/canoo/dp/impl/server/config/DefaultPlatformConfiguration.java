/*
 * Copyright 2015-2018 Canoo Engineering AG.
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
package com.canoo.dp.impl.server.config;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.server.bootstrap.modules.CorsModule;
import com.canoo.dp.impl.server.bootstrap.modules.ServerTimingModule;
import com.canoo.platform.core.PlatformConfiguration;
import org.apiguardian.api.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public final class DefaultPlatformConfiguration implements PlatformConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultPlatformConfiguration.class);

    public static final String USE_CROSS_SITE_ORIGIN_FILTER = "useCrossSiteOriginFilter";

    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "accessControlAllowHeaders";

    public static final String ACCESS_CONTROL_ALLOW_METHODS = "accessControlAllowMethods";

    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "accessControlAllowCredentials";

    public static final String ACCESS_CONTROL_MAXAGE = "accessControlMaxAge";

    public static final String SESSION_TIMEOUT = "sessionTimeout";

    public static final String ROOT_PACKAGE_FOR_CLASSPATH_SCAN = "rootPackageForClasspathScan";

    public static final String MBEAN_REGISTRATION = "mBeanRegistration";

    public static final String PLATFORM_ACTIVE = "active";

    public static final String MAX_CLIENTS_PER_SESSION = "maxClientsPerSession";

    public static final String ID_FILTER_URL_MAPPINGS = "idFilterUrlMappings";

    public static final String CORS_ENDPOINTS_URL_MAPPINGS = "corsUrlMappings";

    public final static int SESSION_TIMEOUT_DEFAULT_VALUE = 900;

    public final static boolean USE_CROSS_SITE_ORIGIN_FILTER_DEFAULT_VALUE = true;

    public final static boolean M_BEAN_REGISTRATION_DEFAULT_VALUE = false;

    public final static boolean ACTIVE_DEFAULT_VALUE = true;

    public final static List<String> ACCESS_CONTROL_ALLOW_HEADERS_DEFAULT_VALUE = Arrays.asList("Content-Type", "x-requested-with", "origin", "authorization", "accept", "client-security-token", "X-platform-security-bearer-only");

    public final static List<String> ACCESS_CONTROL_ALLOW_METHODS_DEFAULT_VALUE = Arrays.asList("*");

    public final static boolean ACCESS_CONTROL_ALLOW_CREDENTIALS_DEFAULT_VALUE = true;

    public final static long ACCESS_CONTROL_MAX_AGE_DEFAULT_VALUE = 86400;

    public final static int MAX_CLIENTS_PER_SESSION_DEFAULT_VALUE = 10;

    public final static List<String> ID_FILTER_URL_MAPPINGS_DEFAULT_VALUE = Arrays.asList("/dolphin");

    public final static List<String> CORS_ENDPOINTS_URL_MAPPINGS_DEFAULT_VALUE = Arrays.asList("/*");

    private final Properties internalProperties;

    public DefaultPlatformConfiguration() {
        this(new Properties());
        setIntProperty(SESSION_TIMEOUT, SESSION_TIMEOUT_DEFAULT_VALUE);
        setBooleanProperty(USE_CROSS_SITE_ORIGIN_FILTER, USE_CROSS_SITE_ORIGIN_FILTER_DEFAULT_VALUE);
        setBooleanProperty(MBEAN_REGISTRATION, M_BEAN_REGISTRATION_DEFAULT_VALUE);
        setBooleanProperty(PLATFORM_ACTIVE, ACTIVE_DEFAULT_VALUE);
        setListProperty(ACCESS_CONTROL_ALLOW_HEADERS, ACCESS_CONTROL_ALLOW_HEADERS_DEFAULT_VALUE);
        setListProperty(ACCESS_CONTROL_ALLOW_METHODS, ACCESS_CONTROL_ALLOW_METHODS_DEFAULT_VALUE);
        setBooleanProperty(ACCESS_CONTROL_ALLOW_CREDENTIALS, ACCESS_CONTROL_ALLOW_CREDENTIALS_DEFAULT_VALUE);
        setLongProperty(ACCESS_CONTROL_MAXAGE, ACCESS_CONTROL_MAX_AGE_DEFAULT_VALUE);
        setIntProperty(MAX_CLIENTS_PER_SESSION, MAX_CLIENTS_PER_SESSION_DEFAULT_VALUE);
        setListProperty(ID_FILTER_URL_MAPPINGS, ID_FILTER_URL_MAPPINGS_DEFAULT_VALUE);
        setBooleanProperty(CorsModule.CORS_MODULE_ACTIVE, true);
        setBooleanProperty(ServerTimingModule.SERVER_TIMING_MODULE_ACTIVE_PROPERTY_NAME, true);
        setListProperty(CORS_ENDPOINTS_URL_MAPPINGS, CORS_ENDPOINTS_URL_MAPPINGS_DEFAULT_VALUE);
    }

    public DefaultPlatformConfiguration(final Properties internalProperties) {
        Assert.requireNonNull(internalProperties, "internalProperties");
        this.internalProperties = internalProperties;
    }

    public boolean containsProperty(final String key) {
        return internalProperties.containsKey(key);
    }

    public boolean getBooleanProperty(final String key, final boolean defaultValue) {
        return Boolean.parseBoolean(internalProperties.getProperty(key, defaultValue + ""));
    }

    public boolean getBooleanProperty(final String key) {
        return getBooleanProperty(key, false);
    }

    public int getIntProperty(final String key, final int defaultValue) {
        return Integer.parseInt(internalProperties.getProperty(key, defaultValue + ""));
    }

    public long getLongProperty(final String key, final long defaultValue) {
        return Long.parseLong(internalProperties.getProperty(key, defaultValue + ""));
    }

    public List<String> getListProperty(final String key) {
        return getListProperty(key, Collections.<String>emptyList());
    }

    public List<String> getListProperty(final String key, final List<String> defaultValues) {
        final String value = internalProperties.getProperty(key);
        if (value != null) {
            return Arrays.asList(value.split(","));
        }
        return defaultValues;
    }

    public String getProperty(final String key, final String defaultValue) {
        return internalProperties.getProperty(key, defaultValue);
    }

    public String getProperty(final String key) {
        return internalProperties.getProperty(key);
    }

    public Set<String> getPropertyKeys() {
        final Set<String> ret = new HashSet<>();
        for (final Object key : internalProperties.keySet()) {
            if (key != null) {
                ret.add(key.toString());
            }
        }
        return ret;
    }

    public void setIntProperty(final String key, final int value) {
        setProperty(key, Integer.toString(value));
    }

    public void setLongProperty(final String key, final long value) {
        setProperty(key, Long.toString(value));
    }

    public void setBooleanProperty(final String key, final boolean value) {
        setProperty(key, Boolean.toString(value));
    }

    public void setListProperty(final String key, final List<String> values) {
        if (values == null) {
            setProperty(key, null);
        } else if (values.isEmpty()) {
            setProperty(key, "");
        } else {
            final StringBuilder builder = new StringBuilder();
            for (String value : values) {
                builder.append(value + ", ");
            }
            builder.setLength(builder.length() - 2);
            setProperty(key, builder.toString());
        }
    }

    public void setProperty(final String key, final String value) {
        if (value == null) {
            LOG.warn("Setting property '{}' to null value will be ignored.");
        } else {
            internalProperties.setProperty(key, value);
        }
    }

    public void log() {
        final Set<Map.Entry<Object, Object>> properties = internalProperties.entrySet();
        for (Map.Entry property : properties) {
            LOG.debug("Dolphin Platform starts with value for " + property.getKey() + " = " + property.getValue());
        }
    }
}


