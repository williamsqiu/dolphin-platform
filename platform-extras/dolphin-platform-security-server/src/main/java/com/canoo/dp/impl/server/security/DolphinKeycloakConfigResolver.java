package com.canoo.dp.impl.server.security;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.server.security.SecurityException;
import org.apiguardian.api.API;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;

import java.util.Optional;

import static com.canoo.dp.impl.security.SecurityHttpHeader.APPLICATION_NAME_HEADER;
import static com.canoo.dp.impl.security.SecurityHttpHeader.BEARER_ONLY_HEADER;
import static com.canoo.dp.impl.security.SecurityHttpHeader.REALM_NAME_HEADER;
import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.19.0", status = INTERNAL)
public class DolphinKeycloakConfigResolver implements KeycloakConfigResolver {


    private static KeycloakConfiguration configuration;

    public KeycloakDeployment resolve(final HttpFacade.Request request) {
        Assert.requireNonNull(request, "request");

        final String realmName = Optional.ofNullable(request.getHeader(REALM_NAME_HEADER)).
                orElse(configuration.getRealmName());
        final String applicationName = Optional.ofNullable(request.getHeader(APPLICATION_NAME_HEADER)).
                orElse(configuration.getApplicationName());
        final String authEndPoint = configuration.getAuthEndpoint();

        Optional.ofNullable(realmName).orElseThrow(() -> new SecurityException("Realm name for security check is not configured!"));
        Optional.ofNullable(applicationName).orElseThrow(() -> new SecurityException("Application name for security check is not configured!"));
        Optional.ofNullable(authEndPoint).orElseThrow(() -> new SecurityException("Auth endpoint for security check is not configured!"));

        final AdapterConfig adapterConfig = new AdapterConfig();
        adapterConfig.setRealm(realmName);
        adapterConfig.setResource(applicationName);
        adapterConfig.setAuthServerUrl(authEndPoint);
        Optional.ofNullable(request.getHeader(BEARER_ONLY_HEADER)).
                ifPresent(v -> adapterConfig.setBearerOnly(true));
        return KeycloakDeploymentBuilder.build(adapterConfig);
    }

    public static void setConfiguration(final KeycloakConfiguration configuration) {
        Assert.requireNonNull(configuration, "configuration");
        DolphinKeycloakConfigResolver.configuration = configuration;
    }
}
