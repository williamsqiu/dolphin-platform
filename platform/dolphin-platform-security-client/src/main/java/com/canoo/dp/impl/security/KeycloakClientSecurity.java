package com.canoo.dp.impl.security;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.HttpURLConnectionFactory;
import org.keycloak.admin.client.Keycloak;

public class KeycloakClientSecurity {

    private final String authEndpoint;

    private final String realmName;

    private final String appName;

    public KeycloakClientSecurity(final String authEndpoint, final String realmName, final String appName) {
        this.appName = Assert.requireNonBlank(appName, "appName");
        this.authEndpoint = Assert.requireNonBlank(authEndpoint, "authEndpoint");
        this.realmName = Assert.requireNonBlank(realmName, "realmName");
    }

    public KeycloakAuthorizationResult login(String userName, String password) {
        final Keycloak keycloak = Keycloak.getInstance(
                authEndpoint,
                realmName,
                userName,
                password,
                appName);

        String secToken = keycloak.tokenManager().getAccessTokenString();
        keycloak.close();
        throw new RuntimeException("Not yet implemented");
    }

    public KeycloakAuthorizationResult logout() {
        throw new RuntimeException("Not yet implemented");
    }

    public boolean isAuthorized() {
        throw new RuntimeException("Not yet implemented");
    }

    public HttpURLConnectionFactory createConnectionFactory() {
        throw new RuntimeException("Not yet implemented");
    }

}
