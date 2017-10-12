package com.canoo.dp.impl.platform.client.http.cookie;

import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.http.HttpURLConnectionHandler;
import com.canoo.platform.client.http.spi.ResponseHandlerProvider;

public class CookieResponseHandlerProvider implements ResponseHandlerProvider {

    @Override
    public HttpURLConnectionHandler getHandler(ClientConfiguration configuration) {
        return new CookieResponseHandler(PlatformClient.getService(HttpClientCookieHandler.class));
    }
}
