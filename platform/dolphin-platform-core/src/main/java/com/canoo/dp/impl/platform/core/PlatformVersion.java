package com.canoo.dp.impl.platform.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PlatformVersion {

    private static final String DEFAULT_LOCATION = "build.properties";

    private static final String VERSION_PROPERTY_NAME = "version";

    private static final String BUILD_DATE_PROPERTY_NAME = "buildDate";

    private static final String BUILD_TIME_PROPERTY_NAME = "buildTime";

    private Properties properties;

    public String getBuildTime() throws IOException {
        return getBuildProperties().getProperty(BUILD_TIME_PROPERTY_NAME);
    }

    public String getBuildDate() throws IOException {
        return getBuildProperties().getProperty(BUILD_DATE_PROPERTY_NAME);
    }

    public String getVersion() throws IOException {
        return getBuildProperties().getProperty(VERSION_PROPERTY_NAME);
    }

    public Properties getBuildProperties() throws IOException {
        return getBuildProperties(PlatformVersion.class.getClassLoader());
    }

    private synchronized Properties getBuildProperties(final ClassLoader classLoader) throws IOException {
        Assert.requireNonNull(classLoader, "classLoader");
        if(properties != null) {
            return properties;
        }
        try (final InputStream inputStream = classLoader.getResourceAsStream(DEFAULT_LOCATION)) {
            if (inputStream != null) {
                properties = new Properties();
                properties.load(inputStream);
                return properties;
            }
        }
        throw new RuntimeException("Can not load properties!");
    }

}
