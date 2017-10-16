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
            throw new RuntimeException("Not yet implemented");
        });
    }

    public boolean isAuthorized() {
        return accessToken != null;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
