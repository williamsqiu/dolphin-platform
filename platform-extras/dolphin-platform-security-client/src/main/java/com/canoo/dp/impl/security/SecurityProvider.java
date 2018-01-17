package com.canoo.dp.impl.security;

import com.canoo.dp.impl.platform.client.AbstractServiceProvider;
import com.canoo.dp.impl.platform.core.Assert;
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
    protected Security createService(final ClientConfiguration configuration) {
        Assert.requireNonNull(configuration, "configuration");


        return new KeycloakSecurity(configuration);
    }
}
