package com.canoo.dp.impl.server.security;

import com.canoo.platform.server.security.Security;
import com.canoo.platform.server.security.User;
import org.keycloak.KeycloakSecurityContext;

import java.util.Optional;

public class SecurityKeycloakImpl implements Security {

    private final User user;

    public SecurityKeycloakImpl(final KeycloakSecurityContext keycloakSecurityContext) {
        this.user = Optional.ofNullable(keycloakSecurityContext).map(c -> new UserKeycloakImpl(keycloakSecurityContext)).orElse(null);
    }

    @Override
    public User getUser() {
        return user;
    }
}
