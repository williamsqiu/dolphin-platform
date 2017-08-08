package com.canoo.dp.impl.server.security;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.server.security.User;
import org.keycloak.KeycloakSecurityContext;

import java.util.stream.Stream;

public class UserKeycloakImpl implements User {

    private final KeycloakSecurityContext keycloakSecurityContext;

    public UserKeycloakImpl(KeycloakSecurityContext keycloakSecurityContext) {
        this.keycloakSecurityContext = Assert.requireNonNull(keycloakSecurityContext, "keycloakSecurityContext");
    }

    @Override
    public Stream<String> getRoles() {
        return keycloakSecurityContext.getToken().getRealmAccess().getRoles().stream();
    }

    @Override
    public String getEmail() {
        return keycloakSecurityContext.getIdToken().getEmail();
    }

    @Override
    public String getUserName() {
        return keycloakSecurityContext.getIdToken().getPreferredUsername();
    }

    @Override
    public String getFirstName() {
        return keycloakSecurityContext.getIdToken().getGivenName();
    }

    @Override
    public String getLastName() {
        return keycloakSecurityContext.getIdToken().getFamilyName();
    }

}
