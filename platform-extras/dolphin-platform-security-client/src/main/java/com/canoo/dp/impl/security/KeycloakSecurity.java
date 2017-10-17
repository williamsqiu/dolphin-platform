package com.canoo.dp.impl.security;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.security.Security;
import org.keycloak.admin.client.Keycloak;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class KeycloakSecurity implements Security {

    private final String authEndpoint;

    private final String realmName;

    private final String appName;

    private final ExecutorService backgroundExecutor;

    private String accessToken;

    public KeycloakSecurity(final String authEndpoint, final String realmName, final String appName, final ExecutorService backgroundExecutor) {
        this.appName = Assert.requireNonBlank(appName, "appName");
        this.authEndpoint = Assert.requireNonBlank(authEndpoint, "authEndpoint");
        this.realmName = Assert.requireNonBlank(realmName, "realmName");
        this.backgroundExecutor = Assert.requireNonNull(backgroundExecutor, "backgroundExecutor");
    }

    @Override
    public Future<Void> login(final String user, final String password) {
        return backgroundExecutor.submit(() -> {
            //This is the ADMIN login
            //It would be better to just do a REST request.

            //Sample: https://github.com/aerogear/aerogear-unifiedpush-server/blob/master/node.js/directgranttest.js

            //Result contains refresh token and Access token.
            //Access Token contains exprire date
            //if this reached do call with refresh token first.

            //https://github.com/keycloak/keycloak/tree/master/adapters/oidc/js/src/main/resources

            //Setup for the realm / client -> "Direct Grand" must be enabled in keycloak admin

            final Keycloak keycloak = Keycloak.getInstance(
                    authEndpoint,
                    realmName,
                    user,
                    password,
                    appName);
            accessToken = keycloak.tokenManager().getAccessTokenString();
            return null;
        });
    }

    @Override
    public Future<Void> logout() {
        return backgroundExecutor.submit(() -> {
            //REST Endpoint at Keycloak site -> Call must be done manually
            //http://www.keycloak.org/docs-api/3.3/rest-api/index.html
            //POST /admin/realms/{realm}/users/{id}/logout

            throw new RuntimeException("Not yet implemented");
        });
    }

    public boolean isAuthorized() {
        //REST Endpoint at Keycloak site -> Call must be done manually
        //http://www.keycloak.org/docs-api/3.3/rest-api/index.html
        //See /admin/realms/{realm}/users/{id}/sessions

        return accessToken != null;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
