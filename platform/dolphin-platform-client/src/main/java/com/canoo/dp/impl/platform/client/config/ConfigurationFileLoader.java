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
package com.canoo.dp.impl.platform.client.config;

import com.canoo.dp.impl.platform.client.DefaultClientConfiguration;
import com.canoo.dp.impl.platform.core.Assert;
import org.apiguardian.api.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class ConfigurationFileLoader {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationFileLoader.class);

    private static final String DEFAULT_LOCATION = "dolphin.properties";

    private static final String JAR_LOCATION = "META-INF/dolphin.properties";

    private ConfigurationFileLoader() {
    }

    public static DefaultClientConfiguration loadConfiguration() {
        final DefaultClientConfiguration configuration = createConfiguration();
        Assert.requireNonNull(configuration, "configuration");

        LOG.debug("Configuration created with {} properties", configuration.getPropertyKeys().size());
        if(LOG.isTraceEnabled()) {
            for(String key : configuration.getPropertyKeys()) {
                LOG.debug("Dolphin Platform configured with '{}'='{}'", key, configuration.getProperty(key, null));
            }
        }

        return configuration;
    }

    private static DefaultClientConfiguration createConfiguration() {
        try {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            try (final InputStream inputStream = classLoader.getResourceAsStream(DEFAULT_LOCATION)) {
                if (inputStream != null) {
                    return readConfig(inputStream);
                }
            }

            try (final InputStream inputStream = classLoader.getResourceAsStream(JAR_LOCATION)) {
                if (inputStream != null) {
                    return readConfig(inputStream);
                }
            }

            return new DefaultClientConfiguration();
        } catch (IOException e) {
            throw new RuntimeException("Can not create configuration!", e);
        }
    }

    private static DefaultClientConfiguration readConfig(final InputStream input) throws IOException {
        Assert.requireNonNull(input, "input");
        final Properties prop = new Properties();
        prop.load(input);

        return new DefaultClientConfiguration(prop);
    }
}
