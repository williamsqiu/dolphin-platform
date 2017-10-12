package com.canoo.dp.impl.platform.client.session;

import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.http.HttpURLConnectionHandler;
import com.canoo.platform.client.http.spi.RequestHandlerProvider;
import com.canoo.platform.client.session.ClientSessionStore;

public class ClientSessionRequestHandlerProvider implements RequestHandlerProvider {
    @Override
    public HttpURLConnectionHandler getHandler(ClientConfiguration configuration) {
        return new ClientSessionRequestHandler(PlatformClient.getService(ClientSessionStore.class));
    }
}
