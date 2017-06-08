package com.canoo.dolphin.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

public class PlatformConfiguration implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(PlatformConfiguration.class);

    public static final String USE_CROSS_SITE_ORIGIN_FILTER = "useCrossSiteOriginFilter";

    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "accessControlAllowHeaders";

    public static final String ACCESS_CONTROL_ALLOW_METHODS = "accessControlAllowMethods";

    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "accessControlAllowCredentials";

    public static final String ACCESS_CONTROL_MAXAGE = "accessControlMaxAge";

    public static final String SESSION_TIMEOUT = "sessionTimeout";

    public static final String ROOT_PACKAGE_FOR_CLASSPATH_SCAN = "rootPackageForClasspathScan";

    public static final String MBEAN_REGISTRATION = "mBeanRegistration";

    public static final String PLATFORM_ACTIVE = "active";

    private final static String SESSION_TIMEOUT_DEFAULT_VALUE = "900";

    private final static String USE_CROSS_SITE_ORIGIN_FILTER_DEFAULT_VALUE = "true";

    private final static String M_BEAN_REGISTRATION_DEFAULT_VALUE = "false";

    private final static String ROOT_PACKAGE_FOR_CLASSPATH_SCAN_DEFAULT_VALUE = null;

    private final static String ACTIVE_DEFAULT_VALUE = "true";

    private final static String ACCESS_CONTROL_ALLOW_HEADERS_DEFAULT_VALUE = "Content-Type,x-requested-with,origin,authorization,accept,client-security-token";

    private final static String ACCESS_CONTROL_ALLOW_METHODS_DEFAULT_VALUE = "*";

    private final static String ACCESS_CONTROL_ALLOW_CREDENTIALS_DEFAULT_VALUE = "true";

    private final static String ACCESS_CONTROL_MAX_AGE_DEFAULT_VALUE = "86400";

    private final Properties internalProperties;

    public PlatformConfiguration() {
        this(new Properties());
        setProperty(SESSION_TIMEOUT, SESSION_TIMEOUT_DEFAULT_VALUE);
        setProperty(USE_CROSS_SITE_ORIGIN_FILTER, USE_CROSS_SITE_ORIGIN_FILTER_DEFAULT_VALUE);
        setProperty(MBEAN_REGISTRATION, M_BEAN_REGISTRATION_DEFAULT_VALUE);
        setProperty(ROOT_PACKAGE_FOR_CLASSPATH_SCAN, ROOT_PACKAGE_FOR_CLASSPATH_SCAN_DEFAULT_VALUE);
        setProperty(PLATFORM_ACTIVE, ACTIVE_DEFAULT_VALUE);
        setProperty(ACCESS_CONTROL_ALLOW_HEADERS, ACCESS_CONTROL_ALLOW_HEADERS_DEFAULT_VALUE);
        setProperty(ACCESS_CONTROL_ALLOW_METHODS, ACCESS_CONTROL_ALLOW_METHODS_DEFAULT_VALUE);
        setProperty(ACCESS_CONTROL_ALLOW_CREDENTIALS, ACCESS_CONTROL_ALLOW_CREDENTIALS_DEFAULT_VALUE);
        setProperty(ACCESS_CONTROL_MAXAGE, ACCESS_CONTROL_MAX_AGE_DEFAULT_VALUE);
    }

    public PlatformConfiguration(final Properties internalProperties) {
        this.internalProperties = internalProperties;
    }

    public boolean containsProperty(final String key) {
        return internalProperties.containsKey(key);
    }

    public String getProperty(final String key) {
        return getProperty(key, null);
    }

    public String getProperty(final String key, final String defaultValue) {
        return internalProperties.getProperty(key, defaultValue);
    }

    public Set<String> getPropertyKeys() {
        Set<String> ret = new HashSet<>();
        for (Object key : internalProperties.keySet()) {
            if (key != null) {
                ret.add(key.toString());
            }
        }
        return ret;
    }

    public void setProperty(final String key, final String value) {
        if (value == null) {
            LOG.warn("Setting property '{}' to null value will be ignored. Only value will be used if property is present");
        } else {
            internalProperties.setProperty(key, value);
        }
    }

    public int getSessionTimeout() {
        return Integer.parseInt(internalProperties.getProperty(SESSION_TIMEOUT, SESSION_TIMEOUT_DEFAULT_VALUE));
    }

    public boolean isUseCrossSiteOriginFilter() {
        return Boolean.parseBoolean(internalProperties.getProperty(USE_CROSS_SITE_ORIGIN_FILTER, USE_CROSS_SITE_ORIGIN_FILTER_DEFAULT_VALUE));
    }

    public boolean isMBeanRegistration() {
        return Boolean.parseBoolean(internalProperties.getProperty(MBEAN_REGISTRATION, M_BEAN_REGISTRATION_DEFAULT_VALUE));
    }

    public String getRootPackageForClasspathScan() {
        return internalProperties.getProperty(ROOT_PACKAGE_FOR_CLASSPATH_SCAN, ROOT_PACKAGE_FOR_CLASSPATH_SCAN_DEFAULT_VALUE);
    }

    public List<String> getAccessControlAllowHeaders() {
        return Arrays.asList(internalProperties.getProperty(ACCESS_CONTROL_ALLOW_HEADERS, ACCESS_CONTROL_ALLOW_HEADERS_DEFAULT_VALUE).split(","));
    }

    public List<String> getAccessControlAllowMethods() {
        return Arrays.asList(internalProperties.getProperty(ACCESS_CONTROL_ALLOW_METHODS, ACCESS_CONTROL_ALLOW_METHODS_DEFAULT_VALUE).split(","));
    }

    public boolean isAccessControlAllowCredentials() {
        return Boolean.parseBoolean(internalProperties.getProperty(ACCESS_CONTROL_ALLOW_CREDENTIALS, ACCESS_CONTROL_ALLOW_CREDENTIALS_DEFAULT_VALUE));
    }

    public long getAccessControlMaxAge() {
        return Long.parseLong(internalProperties.getProperty(ACCESS_CONTROL_MAXAGE, ACCESS_CONTROL_MAX_AGE_DEFAULT_VALUE));
    }

    public boolean isActive() {
        return Boolean.parseBoolean(internalProperties.getProperty(PLATFORM_ACTIVE, ACTIVE_DEFAULT_VALUE));
    }

    public void log() {
        Set<Map.Entry<Object, Object>> properties = internalProperties.entrySet();
        for (Map.Entry property : properties) {
            LOG.debug("Dolphin Platform starts with value for " + property.getKey() + " = " + property.getValue());
        }
    }
}


