package com.canoo.dp.impl.platform.client.http.cookie;

import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.http.HttpURLConnectionHandler;
import com.canoo.platform.client.http.spi.RequestHandlerProvider;

public class CookieRequestHandlerProvider implements RequestHandlerProvider {

    @Override
    public HttpURLConnectionHandler getHandler(ClientConfiguration configuration) {
        return new CookieRequestHandler(PlatformClient.getService(HttpClientCookieHandler.class));
    }
}
