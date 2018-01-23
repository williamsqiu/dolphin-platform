package com.canoo.dp.impl.server.security.spring;

import com.canoo.dp.impl.platform.core.Assert;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.function.Supplier;

import static com.canoo.dp.impl.security.SecurityHttpHeader.AUTHORIZATION_HEADER;
import static com.canoo.dp.impl.security.SecurityHttpHeader.BEARER;

public class SecurityClientRequestFactory extends HttpComponentsClientHttpRequestFactory implements ClientHttpRequestFactory {

    private final Supplier<String> securityTokenSupplier;

    public SecurityClientRequestFactory(final Supplier<String> securityTokenSupplier) {
        super(HttpClients.custom()
                .disableCookieManagement()
                .build()
        );
        this.securityTokenSupplier = Assert.requireNonNull(securityTokenSupplier, "securityTokenSupplier");
    }

    @Override
    protected void postProcessHttpRequest(HttpUriRequest request) {
        final String token = securityTokenSupplier.get();
        request.setHeader(AUTHORIZATION_HEADER, BEARER + token);
    }
}
