package com.canoo.dp.impl.server.security;

import org.apiguardian.api.API;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.19.0", status = INTERNAL)
public class DolphinKeycloakConfigResolver implements KeycloakConfigResolver {

    private static KeycloakConfiguration configuration;

    public KeycloakDeployment resolve(final HttpFacade.Request request) {

        //keycloak.auth-server-url=http://localhost:8180/auth
        //keycloak.realm=canoo
        //keycloak.public-client=true
        //keycloak.resource=product-app

        AdapterConfig adapterConfig = new AdapterConfig();
        adapterConfig.setRealm(configuration.getRealmName());
        adapterConfig.setAuthServerUrl(configuration.getAuthEndpoint());
        adapterConfig.setPublicClient(true);
        adapterConfig.setResource(configuration.getApplicationName());
        return KeycloakDeploymentBuilder.build(adapterConfig);
    }

    public static void setConfiguration(KeycloakConfiguration configuration) {
        DolphinKeycloakConfigResolver.configuration = configuration;
    }
}
