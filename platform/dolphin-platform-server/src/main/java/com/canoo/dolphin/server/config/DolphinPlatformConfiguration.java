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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;

/**
 * This class defines the configuration of the Dolphin Platform. Normally the configuration is created based
 * on defaults and a property file (see {@link ConfigurationFileLoader}).
 */
public class DolphinPlatformConfiguration implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DolphinPlatformConfiguration.class);

    private static final String OPEN_DOLPHIN_LOG_LEVEL = "openDolphinLogLevel";

    private static final String DOLPHIN_PLATFORM_SERVLET_MAPPING = "servletMapping";

    private static final String USE_CROSS_SITE_ORIGIN_FILTER = "useCrossSiteOriginFilter";

    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "accessControlAllowHeaders";

    private static final String ACCESS_CONTROL_ALLOW_METHODS = "accessControlAllowMethods";

    private static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "accessControlAllowCredentials";

    private static final String ACCESS_CONTROL_MAXAGE = "accessControlMaxAge";

    private static final String USE_SESSION_INVALIDATION_SERVLET = "useSessionInvalidationServlet";

    private static final String GARBAGE_COLLECTION_ACTIVE = "garbageCollectionActive";

    private static final String SESSION_TIMEOUT = "sessionTimeout";

    private static final String MAX_CLIENTS_PER_SESSION = "maxClientsPerSession";

    private static final String ID_FILTER_URL_MAPPINGS = "idFilterUrlMappings";

    private static final String ROOT_PACKAGE_FOR_CLASSPATH_SCAN = "rootPackageForClasspathScan";

    private static final String MBEAN_REGISTRATION = "mBeanRegistration";

    private static final String MAX_POLL_TIME = "maxPollTime";

    private static final String EVENTBUS_TYPE = "eventbusType";

    private static final String PLATFORM_ACTIVE = "active";

    private boolean useSessionInvalidationServlet = false;

    public final static int SESSION_TIMEOUT_DEFAULT_VALUE = 15 * 60;

    private boolean useCrossSiteOriginFilter = true;

    private boolean mBeanRegistration = true;

    private String dolphinPlatformServletMapping = "/dolphin";

    private String rootPackageForClasspathScan = null;

    private String eventbusType = DefaultEventBusProvider.DEFAULT_EVENTBUS_NAME;

    private List<String> idFilterUrlMappings = Arrays.asList("/dolphin");

    private Level openDolphinLogLevel = Level.SEVERE;

    private int sessionTimeout = SESSION_TIMEOUT_DEFAULT_VALUE;

    private long maxPollTime = 5_000;

    private int maxClientsPerSession = 10;

    private boolean useGc = true;

    private boolean active = true;

    private List<String> accessControlAllowHeaders = Arrays.asList("Content-Type", "x-requested-with", "origin", "authorization", "accept", "client-security-token");

    private List<String> accessControlAllowMethods = Arrays.asList("*");

    private boolean accessControlAllowCredentials = true;

    private long accessControlMaxAge = 86400;

    private final Properties internalProperties;

    public DolphinPlatformConfiguration() {
        this(new Properties());
    }

    public DolphinPlatformConfiguration(final Properties internalProperties) {
        this.internalProperties = internalProperties;

        if (internalProperties.containsKey(OPEN_DOLPHIN_LOG_LEVEL)) {
            String level = internalProperties.getProperty(OPEN_DOLPHIN_LOG_LEVEL);
            if ("info".equalsIgnoreCase(level.trim())) {
                openDolphinLogLevel = Level.INFO;
            } else if ("severe".equalsIgnoreCase(level.trim())) {
                openDolphinLogLevel = Level.SEVERE;
            } else if ("all".equalsIgnoreCase(level.trim())) {
                openDolphinLogLevel = Level.ALL;
            } else if ("config".equalsIgnoreCase(level.trim())) {
                openDolphinLogLevel = Level.CONFIG;
            } else if ("fine".equalsIgnoreCase(level.trim())) {
                openDolphinLogLevel = Level.FINE;
            } else if ("finer".equalsIgnoreCase(level.trim())) {
                openDolphinLogLevel = Level.FINER;
            } else if ("finest".equalsIgnoreCase(level.trim())) {
                openDolphinLogLevel = Level.FINEST;
            } else if ("off".equalsIgnoreCase(level.trim())) {
                openDolphinLogLevel = Level.OFF;
            } else if ("warning".equalsIgnoreCase(level.trim())) {
                openDolphinLogLevel = Level.WARNING;
            }
        }

        if (internalProperties.containsKey(DOLPHIN_PLATFORM_SERVLET_MAPPING)) {
            dolphinPlatformServletMapping = internalProperties.getProperty(DOLPHIN_PLATFORM_SERVLET_MAPPING);
        }

        if (internalProperties.containsKey(ROOT_PACKAGE_FOR_CLASSPATH_SCAN)) {
            rootPackageForClasspathScan = internalProperties.getProperty(ROOT_PACKAGE_FOR_CLASSPATH_SCAN);
        }

        if (internalProperties.containsKey(MBEAN_REGISTRATION)) {
            mBeanRegistration = Boolean.parseBoolean(internalProperties.getProperty(MBEAN_REGISTRATION));
        }

        if (internalProperties.containsKey(USE_CROSS_SITE_ORIGIN_FILTER)) {
            useCrossSiteOriginFilter = Boolean.parseBoolean(internalProperties.getProperty(DOLPHIN_PLATFORM_SERVLET_MAPPING));
        }

        if (internalProperties.containsKey(ACCESS_CONTROL_ALLOW_HEADERS)) {
            String headers = internalProperties.getProperty(ACCESS_CONTROL_ALLOW_HEADERS);
            if(null != headers){
                accessControlAllowHeaders = Arrays.asList(headers.split(","));
            }
        }

        if (internalProperties.containsKey(ACCESS_CONTROL_ALLOW_METHODS)) {
            String headers = internalProperties.getProperty(ACCESS_CONTROL_ALLOW_METHODS);
            if(null != headers) {
                accessControlAllowMethods = Arrays.asList(headers.split(","));
            }
        }

        if (internalProperties.containsKey(ACCESS_CONTROL_ALLOW_CREDENTIALS)) {
            accessControlAllowCredentials = Boolean.parseBoolean(internalProperties.getProperty(ACCESS_CONTROL_ALLOW_CREDENTIALS));
        }

        if (internalProperties.containsKey(ACCESS_CONTROL_MAXAGE)) {
            accessControlMaxAge = Long.parseLong(internalProperties.getProperty(ACCESS_CONTROL_MAXAGE));
        }

        if (internalProperties.containsKey(USE_SESSION_INVALIDATION_SERVLET)) {
            useSessionInvalidationServlet = Boolean.parseBoolean(internalProperties.getProperty(USE_SESSION_INVALIDATION_SERVLET));
        }

        if (internalProperties.containsKey(PLATFORM_ACTIVE)) {
            active = Boolean.parseBoolean(internalProperties.getProperty(PLATFORM_ACTIVE));
        }

        if (internalProperties.containsKey(GARBAGE_COLLECTION_ACTIVE)) {
            useGc = Boolean.parseBoolean(internalProperties.getProperty(GARBAGE_COLLECTION_ACTIVE));
        }

        if (internalProperties.containsKey(SESSION_TIMEOUT)) {
            sessionTimeout = Integer.parseInt(internalProperties.getProperty(SESSION_TIMEOUT));
        }

        if (internalProperties.containsKey(MAX_CLIENTS_PER_SESSION)) {
            maxClientsPerSession = Integer.parseInt(internalProperties.getProperty(MAX_CLIENTS_PER_SESSION));
        }

        if (internalProperties.containsKey(ID_FILTER_URL_MAPPINGS)) {
            String content = internalProperties.getProperty(ID_FILTER_URL_MAPPINGS);
            idFilterUrlMappings = Arrays.asList(content.split(","));
        }

        if (internalProperties.containsKey(MAX_POLL_TIME)) {
            maxPollTime = Long.parseLong(internalProperties.getProperty(MAX_POLL_TIME));
        }

        if (internalProperties.containsKey(EVENTBUS_TYPE)) {
            eventbusType = internalProperties.getProperty(EVENTBUS_TYPE);
        }
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public boolean isUseCrossSiteOriginFilter() {
        return useCrossSiteOriginFilter;
    }

    public String getDolphinPlatformServletMapping() {
        return dolphinPlatformServletMapping;
    }

    public Level getOpenDolphinLogLevel() {
        return openDolphinLogLevel;
    }

    public int getMaxClientsPerSession() {
        return maxClientsPerSession;
    }

    public boolean isUseSessionInvalidationServlet() {
        return useSessionInvalidationServlet;
    }

    public List<String> getIdFilterUrlMappings() {
        return Collections.unmodifiableList(idFilterUrlMappings);
    }

    public boolean isMBeanRegistration() {
        return mBeanRegistration;
    }

    public String getRootPackageForClasspathScan() {
        return rootPackageForClasspathScan;
    }

    public long getMaxPollTime() {
        return maxPollTime;
    }

    public boolean isUseGc() {
        return useGc;
    }

    public String getProperty(final String key) {
        return internalProperties.getProperty(key);
    }

    public String getEventbusType() {
        return eventbusType;
    }

    public void setUseSessionInvalidationServlet(boolean useSessionInvalidationServlet) {
        this.useSessionInvalidationServlet = useSessionInvalidationServlet;
    }

    public void setUseCrossSiteOriginFilter(boolean useCrossSiteOriginFilter) {
        this.useCrossSiteOriginFilter = useCrossSiteOriginFilter;
    }

    public void setmBeanRegistration(boolean mBeanRegistration) {
        this.mBeanRegistration = mBeanRegistration;
    }

    public void setDolphinPlatformServletMapping(String dolphinPlatformServletMapping) {
        this.dolphinPlatformServletMapping = dolphinPlatformServletMapping;
    }

    public void setRootPackageForClasspathScan(String rootPackageForClasspathScan) {
        this.rootPackageForClasspathScan = rootPackageForClasspathScan;
    }

    public void setEventbusType(String eventbusType) {
        this.eventbusType = eventbusType;
    }

    public void setIdFilterUrlMappings(List<String> idFilterUrlMappings) {
        this.idFilterUrlMappings = idFilterUrlMappings;
    }

    public void setOpenDolphinLogLevel(Level openDolphinLogLevel) {
        this.openDolphinLogLevel = openDolphinLogLevel;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public void setMaxPollTime(long maxPollTime) {
        this.maxPollTime = maxPollTime;
    }

    public void setMaxClientsPerSession(int maxClientsPerSession) {
        this.maxClientsPerSession = maxClientsPerSession;
    }

    public void setUseGc(boolean useGc) {
        this.useGc = useGc;
    }

    public List<String> getAccessControlAllowHeaders() {
        return Collections.unmodifiableList(accessControlAllowHeaders);
    }

    public void setAccessControlAllowHeaders(List<String> accessControlAllowHeaders) {
        this.accessControlAllowHeaders = accessControlAllowHeaders;
    }

    public List<String> getAccessControlAllowMethods() {
        return accessControlAllowMethods;
    }

    public void setAccessControlAllowMethods(List<String> accessControlAllowMethods) {
        this.accessControlAllowMethods = accessControlAllowMethods;
    }

    public static String getUseCrossSiteOriginFilter() {
        return USE_CROSS_SITE_ORIGIN_FILTER;
    }

    public void setAccessControlAllowCredentials(boolean accessControlAllowCredentials) {
        this.accessControlAllowCredentials = accessControlAllowCredentials;
    }

    public boolean isAccessControlAllowCredentials() {
        return accessControlAllowCredentials;
    }

    public long getAccessControlMaxAge() {
        return accessControlMaxAge;
    }

    public void setAccessControlMaxAge(long accessControlMaxAge) {
        this.accessControlMaxAge = accessControlMaxAge;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void log(){
        Set<Map.Entry<Object, Object>> properties = internalProperties.entrySet();
        for(Map.Entry property:properties){
            LOG.debug("Dolphin Platform starts with value for "+ property.getKey() +" = "+ property.getValue());
        }
    }
}

