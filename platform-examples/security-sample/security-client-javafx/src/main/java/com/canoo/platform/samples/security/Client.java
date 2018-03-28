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
package com.canoo.platform.samples.security;

import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.security.Security;
import com.canoo.platform.core.SimpleConfiguration;
import com.canoo.platform.core.http.HttpClient;

import static com.canoo.dp.impl.security.SecurityConfiguration.APPLICATION_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityConfiguration.REALM_PROPERTY_NAME;

public class Client {

    public static void main(final String[] args) throws Exception {
        final HttpClient client = PlatformClient.getService(HttpClient.class);
        final Security security = PlatformClient.getService(Security.class);
        final SimpleConfiguration configuration = new SimpleConfiguration();

        configuration.setProperty(REALM_PROPERTY_NAME, "dolphin-realm");
        configuration.setProperty(APPLICATION_PROPERTY_NAME, "default-dolphin-client");
        configuration.log();

        security.login("user", "password", configuration).get();

        client.get("http://localhost:8080/api/secure/message").
                withoutContent().
                readString().execute().get();
    }
}
