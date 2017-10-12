package com.canoo.dp.impl.platform.client.session;

import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.http.HttpURLConnectionHandler;
import com.canoo.platform.client.http.spi.ResponseHandlerProvider;
import com.canoo.platform.client.session.ClientSessionStore;

public class ClientSessionResponseHandlerProvider implements ResponseHandlerProvider {
    @Override
    public HttpURLConnectionHandler getHandler(ClientConfiguration configuration) {
        return new ClientSessionResponseHandler(PlatformClient.getService(ClientSessionStore.class));
    }
}
