package com.canoo.dp.impl.platform.client.http.cookie;

import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.core.PlatformConfiguration;
import com.canoo.platform.core.http.HttpURLConnectionHandler;
import com.canoo.platform.core.http.spi.RequestHandlerProvider;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class CookieRequestHandlerProvider implements RequestHandlerProvider {

    @Override
    public HttpURLConnectionHandler getHandler(PlatformConfiguration configuration) {
        return new CookieRequestHandler(PlatformClient.getService(HttpClientCookieHandler.class));
    }
}
