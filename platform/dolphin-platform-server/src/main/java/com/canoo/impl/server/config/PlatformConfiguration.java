package com.canoo.impl.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

public final class PlatformConfiguration implements Serializable {

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

    public static final String MAX_CLIENTS_PER_SESSION = "maxClientsPerSession";

    public static final String ID_FILTER_URL_MAPPINGS = "idFilterUrlMappings";

    public static final String CORS_ENDPOINTS_URL_MAPPINGS = "corsUrlMappings";

    private final static int SESSION_TIMEOUT_DEFAULT_VALUE = 900;

    private final static boolean USE_CROSS_SITE_ORIGIN_FILTER_DEFAULT_VALUE = true;

    private final static boolean M_BEAN_REGISTRATION_DEFAULT_VALUE = false;

    private final static String ROOT_PACKAGE_FOR_CLASSPATH_SCAN_DEFAULT_VALUE = null;

    private final static boolean ACTIVE_DEFAULT_VALUE = true;

    private final static List<String> ACCESS_CONTROL_ALLOW_HEADERS_DEFAULT_VALUE = Arrays.asList("Content-Type", "x-requested-with", "origin", "authorization", "accept", "client-security-token");

    private final static List<String> ACCESS_CONTROL_ALLOW_METHODS_DEFAULT_VALUE = Arrays.asList("*");

    private final static boolean ACCESS_CONTROL_ALLOW_CREDENTIALS_DEFAULT_VALUE = true;

    private final static long ACCESS_CONTROL_MAX_AGE_DEFAULT_VALUE = 86400;

    private final static int MAX_CLIENTS_PER_SESSION_DEFAULT_VALUE = 10;

    private final static List<String> ID_FILTER_URL_MAPPINGS_DEFAULT_VALUE = Arrays.asList("/dolphin");

    private final static List<String> CORS_ENDPOINTS_URL_MAPPINGS_DEFAULT_VALUE = Arrays.asList("/*");

    private final Properties internalProperties;

    public PlatformConfiguration() {
        this(new Properties());
        setIntProperty(SESSION_TIMEOUT, SESSION_TIMEOUT_DEFAULT_VALUE);
        setBooleanProperty(USE_CROSS_SITE_ORIGIN_FILTER, USE_CROSS_SITE_ORIGIN_FILTER_DEFAULT_VALUE);
        setBooleanProperty(MBEAN_REGISTRATION, M_BEAN_REGISTRATION_DEFAULT_VALUE);
        setProperty(ROOT_PACKAGE_FOR_CLASSPATH_SCAN, ROOT_PACKAGE_FOR_CLASSPATH_SCAN_DEFAULT_VALUE);
        setBooleanProperty(PLATFORM_ACTIVE, ACTIVE_DEFAULT_VALUE);
        setListProperty(ACCESS_CONTROL_ALLOW_HEADERS, ACCESS_CONTROL_ALLOW_HEADERS_DEFAULT_VALUE);
        setListProperty(ACCESS_CONTROL_ALLOW_METHODS, ACCESS_CONTROL_ALLOW_METHODS_DEFAULT_VALUE);
        setBooleanProperty(ACCESS_CONTROL_ALLOW_CREDENTIALS, ACCESS_CONTROL_ALLOW_CREDENTIALS_DEFAULT_VALUE);
        setLongProperty(ACCESS_CONTROL_MAXAGE, ACCESS_CONTROL_MAX_AGE_DEFAULT_VALUE);
        setIntProperty(MAX_CLIENTS_PER_SESSION, MAX_CLIENTS_PER_SESSION_DEFAULT_VALUE);
        setListProperty(ID_FILTER_URL_MAPPINGS, ID_FILTER_URL_MAPPINGS_DEFAULT_VALUE);
        setListProperty(CORS_ENDPOINTS_URL_MAPPINGS, CORS_ENDPOINTS_URL_MAPPINGS_DEFAULT_VALUE);
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

    public boolean getBooleanProperty(final String key, boolean defaultValue) {
        return Boolean.parseBoolean(internalProperties.getProperty(key, defaultValue + ""));
    }

    public boolean getBooleanProperty(final String key) {
        return getBooleanProperty(key, false);
    }

    public int getIntProperty(final String key, int defaultValue) {
        return Integer.parseInt(internalProperties.getProperty(key, defaultValue + ""));
    }

    public long getLongProperty(final String key, long defaultValue) {
        return Long.parseLong(internalProperties.getProperty(key, defaultValue + ""));
    }

    public List<String> getListProperty(final String key) {
        return getListProperty(key, Collections.<String>emptyList());
    }

    public List<String> getListProperty(final String key, final List<String> defaultValues) {
        final String value = internalProperties.getProperty(key);
        if (value != null) {
            return Arrays.asList(internalProperties.getProperty(key));
        }
        return defaultValues;
    }

    public String getProperty(final String key, final String defaultValue) {
        return internalProperties.getProperty(key, defaultValue);
    }

    public Set<String> getPropertyKeys() {
        final Set<String> ret = new HashSet<>();
        for (Object key : internalProperties.keySet()) {
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
            StringBuilder builder = new StringBuilder();
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

    public int getSessionTimeout() {
        return getIntProperty(SESSION_TIMEOUT, SESSION_TIMEOUT_DEFAULT_VALUE);
    }

    public boolean isUseCrossSiteOriginFilter() {
        return getBooleanProperty(USE_CROSS_SITE_ORIGIN_FILTER, USE_CROSS_SITE_ORIGIN_FILTER_DEFAULT_VALUE);
    }

    public boolean isMBeanRegistration() {
        return getBooleanProperty(MBEAN_REGISTRATION, M_BEAN_REGISTRATION_DEFAULT_VALUE);
    }

    public String getRootPackageForClasspathScan() {
        return getProperty(ROOT_PACKAGE_FOR_CLASSPATH_SCAN, ROOT_PACKAGE_FOR_CLASSPATH_SCAN_DEFAULT_VALUE);
    }

    public List<String> getAccessControlAllowHeaders() {
        return getListProperty(ACCESS_CONTROL_ALLOW_HEADERS, ACCESS_CONTROL_ALLOW_HEADERS_DEFAULT_VALUE);
    }

    public List<String> getAccessControlAllowMethods() {
        return getListProperty(ACCESS_CONTROL_ALLOW_METHODS, ACCESS_CONTROL_ALLOW_METHODS_DEFAULT_VALUE);
    }

    public boolean isAccessControlAllowCredentials() {
        return getBooleanProperty(ACCESS_CONTROL_ALLOW_CREDENTIALS, ACCESS_CONTROL_ALLOW_CREDENTIALS_DEFAULT_VALUE);
    }

    public long getAccessControlMaxAge() {
        return getLongProperty(ACCESS_CONTROL_MAXAGE, ACCESS_CONTROL_MAX_AGE_DEFAULT_VALUE);
    }

    public boolean isActive() {
        return getBooleanProperty(PLATFORM_ACTIVE, ACTIVE_DEFAULT_VALUE);
    }

    public void log() {
        Set<Map.Entry<Object, Object>> properties = internalProperties.entrySet();
        for (Map.Entry property : properties) {
            LOG.debug("Dolphin Platform starts with value for " + property.getKey() + " = " + property.getValue());
        }
    }

    public int getMaxClientsPerSession() {
        return getIntProperty(MAX_CLIENTS_PER_SESSION, MAX_CLIENTS_PER_SESSION_DEFAULT_VALUE);
    }

    public List<String> getIdFilterUrlMappings() {
        return getListProperty(ID_FILTER_URL_MAPPINGS, ID_FILTER_URL_MAPPINGS_DEFAULT_VALUE);
    }

    public List<String> getCorsEndpoints() {
        return getListProperty(CORS_ENDPOINTS_URL_MAPPINGS, CORS_ENDPOINTS_URL_MAPPINGS_DEFAULT_VALUE);
    }
}


