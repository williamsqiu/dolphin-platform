package com.canoo.platform.client.http.spi;

import com.canoo.platform.client.ClientConfiguration;

public interface ResponseHandlerProvider {

    HttpURLConnectionHandler getHandler(ClientConfiguration configuration);
}
