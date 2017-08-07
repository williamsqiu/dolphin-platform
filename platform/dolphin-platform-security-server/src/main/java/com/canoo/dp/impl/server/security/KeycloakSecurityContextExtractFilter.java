package com.canoo.dp.impl.server.security;

import com.canoo.dp.impl.platform.core.Assert;
import org.keycloak.KeycloakSecurityContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class KeycloakSecurityContextExtractFilter implements Filter {

    private final ThreadLocal<KeycloakSecurityContext> contextHolder = new ThreadLocal<KeycloakSecurityContext>();

    private final KeyCloakSecurityExtractor keyCloakSecurityExtractor = new KeyCloakSecurityExtractor();

    public void init(final FilterConfig filterConfig) throws ServletException {}

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        Assert.requireNonNull(chain, "chain");
        contextHolder.set(keyCloakSecurityExtractor.extractContext(request));
        try {
            chain.doFilter(request, response);
        } finally {
            contextHolder.set(null);
        }
    }

    public void destroy() {}

    public KeycloakSecurityContext getContext() {
        return contextHolder.get();
    }
}
