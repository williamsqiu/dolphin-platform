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
package com.canoo.impl.server;

import static com.canoo.dp.impl.server.config.DefaultPlatformConfiguration.USE_CROSS_SITE_ORIGIN_FILTER;
import static com.canoo.dp.impl.server.config.DefaultPlatformConfiguration.USE_CROSS_SITE_ORIGIN_FILTER_DEFAULT_VALUE;
import com.canoo.dp.impl.server.config.DefaultPlatformConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DolphinDefaultPlatformConfigurationTest {

    @Test
    public void testDefaultConfiguration() {
        //given:
        DefaultPlatformConfiguration configuration = new DefaultPlatformConfiguration();

        //then:
        Assert.assertEquals(configuration.getBooleanProperty(USE_CROSS_SITE_ORIGIN_FILTER, USE_CROSS_SITE_ORIGIN_FILTER_DEFAULT_VALUE), true);
    }

}
