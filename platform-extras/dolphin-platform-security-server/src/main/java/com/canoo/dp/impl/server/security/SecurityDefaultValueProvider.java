package com.canoo.dp.impl.server.security;

import com.canoo.platform.server.spi.ConfigurationProviderAdapter;
import org.apiguardian.api.API;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.canoo.dp.impl.security.SecurityConfiguration.APPLICATION_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.APPLICATION_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityConfiguration.AUTH_ENDPOINT_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.AUTH_ENDPOINT_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityConfiguration.REALM_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.REALM_PROPERTY_NAME;
import static com.canoo.dp.impl.server.security.KeycloakConfiguration.SECURE_ENDPOINTS_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.server.security.KeycloakConfiguration.SECURE_ENDPOINTS_PROPERTY_NAME;
import static com.canoo.dp.impl.server.security.KeycloakConfiguration.SECURITY_ACTIVE_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.server.security.KeycloakConfiguration.SECURITY_ACTIVE_PROPERTY_NAME;
import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.19.0", status = INTERNAL)
public class SecurityDefaultValueProvider extends ConfigurationProviderAdapter {

    @Override
    public Map<String, String> getStringProperties() {
        HashMap<String, String> ret = new HashMap<>();
        ret.put(REALM_PROPERTY_NAME, REALM_PROPERTY_DEFAULT_VALUE);
        ret.put(APPLICATION_PROPERTY_NAME, APPLICATION_PROPERTY_DEFAULT_VALUE);
        ret.put(AUTH_ENDPOINT_PROPERTY_NAME, AUTH_ENDPOINT_PROPERTY_DEFAULT_VALUE);
        return ret;
    }

    @Override
    public Map<String, Boolean> getBooleanProperties() {
        return Collections.singletonMap(SECURITY_ACTIVE_PROPERTY_NAME, SECURITY_ACTIVE_PROPERTY_DEFAULT_VALUE);
    }

    @Override
    public Map<String, List<String>> getListProperties() {
        return Collections.singletonMap(SECURE_ENDPOINTS_PROPERTY_NAME, Collections.<String>singletonList(SECURE_ENDPOINTS_PROPERTY_DEFAULT_VALUE));
    }
}
