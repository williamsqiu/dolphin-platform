package com.canoo.platform.client.http.spi;

import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.http.HttpURLConnectionHandler;

public interface ResponseHandlerProvider {

    HttpURLConnectionHandler getHandler(ClientConfiguration configuration);
}
