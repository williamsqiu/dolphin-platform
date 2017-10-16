package com.canoo.platform.core.http.spi;

import com.canoo.platform.core.PlatformConfiguration;
import com.canoo.platform.core.http.HttpURLConnectionHandler;

public interface ResponseHandlerProvider {

    HttpURLConnectionHandler getHandler(PlatformConfiguration configuration);
}
