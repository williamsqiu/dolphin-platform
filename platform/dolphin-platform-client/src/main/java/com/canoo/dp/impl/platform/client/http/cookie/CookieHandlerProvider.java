package com.canoo.dp.impl.platform.client.http.cookie;

import com.canoo.dp.impl.platform.client.AbstractServiceProvider;
import com.canoo.platform.client.ClientConfiguration;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class CookieHandlerProvider extends AbstractServiceProvider<HttpClientCookieHandler> {

    public CookieHandlerProvider() {
        super(HttpClientCookieHandler.class);
    }

    @Override
    protected HttpClientCookieHandler createService(ClientConfiguration configuration) {
        return new HttpClientCookieHandler(configuration.getCookieStore());
    }
}
