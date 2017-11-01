package com.canoo.dp.impl.server.security;

import com.canoo.dp.impl.platform.core.Assert;
import org.apiguardian.api.API;
import org.keycloak.KeycloakSecurityContext;

import javax.servlet.ServletRequest;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.19.0", status = INTERNAL)
public class KeyCloakSecurityExtractor {

    public KeycloakSecurityContext extractContext(final ServletRequest request) {
        Assert.requireNonNull(request, "request");
        final KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        return context;
    }
}
