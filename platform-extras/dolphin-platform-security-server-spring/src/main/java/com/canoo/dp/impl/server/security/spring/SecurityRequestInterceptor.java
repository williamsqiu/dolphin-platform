package com.canoo.dp.impl.server.security.spring;

import com.canoo.dp.impl.platform.core.Assert;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;
import java.util.function.Supplier;

import static com.canoo.dp.impl.security.SecurityHttpHeader.AUTHORIZATION_HEADER;
import static com.canoo.dp.impl.security.SecurityHttpHeader.BEARER;

public class SecurityRequestInterceptor implements ClientHttpRequestInterceptor {

    private final Supplier<String> securityTokenSupplier;

    public SecurityRequestInterceptor(final Supplier<String> securityTokenSupplier) {
        this.securityTokenSupplier = Assert.requireNonNull(securityTokenSupplier, "securityTokenSupplier");
    }

    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {
        final HttpRequestWrapper wrapper = new HttpRequestWrapper(request);
        final String token = securityTokenSupplier.get();
        if(token != null) {
            wrapper.getHeaders().add(AUTHORIZATION_HEADER, BEARER + token);
        }
        return execution.execute(wrapper, body);
    }
}
