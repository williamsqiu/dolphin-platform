package com.canoo.dp.impl.server.security;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;

public class DolphinKeycloakConfigResolver implements KeycloakConfigResolver {

    private static KeycloakConfiguration configuration;

    public KeycloakDeployment resolve(final HttpFacade.Request request) {
        final KeycloakDeployment deployment = new KeycloakDeployment();
        deployment.setRealm(configuration.getRealmName());
        final AdapterConfig adapterConfig = new AdapterConfig();
        adapterConfig.setAuthServerUrl(configuration.getAuthEndpoint());
        deployment.setAuthServerBaseUrl(adapterConfig);
        deployment.setResourceName(configuration.getApplicationName());
        return deployment;
    }

    public static void setConfiguration(KeycloakConfiguration configuration) {
        DolphinKeycloakConfigResolver.configuration = configuration;
    }
}
