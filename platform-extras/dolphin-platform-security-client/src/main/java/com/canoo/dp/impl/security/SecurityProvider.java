package com.canoo.dp.impl.security;

import com.canoo.dp.impl.platform.client.AbstractServiceProvider;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.security.Security;
import org.apiguardian.api.API;

import static com.canoo.dp.impl.security.SecurityConfiguration.APPLICATION_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.APPLICATION_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityConfiguration.AUTH_ENDPOINT_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.AUTH_ENDPOINT_PROPERTY_NAME;
import static com.canoo.dp.impl.security.SecurityConfiguration.REALM_PROPERTY_DEFAULT_VALUE;
import static com.canoo.dp.impl.security.SecurityConfiguration.REALM_PROPERTY_NAME;
import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.19.0", status = INTERNAL)
public class SecurityProvider extends AbstractServiceProvider<Security> {

    public SecurityProvider() {
        super(Security.class);
    }

    @Override
    protected Security createService(ClientConfiguration configuration) {
        final String authEndpoint = configuration.getProperty(AUTH_ENDPOINT_PROPERTY_NAME, AUTH_ENDPOINT_PROPERTY_DEFAULT_VALUE);
        final String realmName = configuration.getProperty(REALM_PROPERTY_NAME, REALM_PROPERTY_DEFAULT_VALUE);
        final String appName = configuration.getProperty(APPLICATION_PROPERTY_NAME, APPLICATION_PROPERTY_DEFAULT_VALUE);

        return new KeycloakSecurity(authEndpoint, realmName, appName, configuration.getBackgroundExecutor());
    }
}
