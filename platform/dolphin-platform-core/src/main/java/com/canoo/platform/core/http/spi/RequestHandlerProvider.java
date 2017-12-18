package com.canoo.platform.core.http.spi;

import com.canoo.platform.core.PlatformConfiguration;
import com.canoo.platform.core.http.HttpURLConnectionHandler;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
@FunctionalInterface
public interface RequestHandlerProvider {

    HttpURLConnectionHandler getHandler(PlatformConfiguration configuration);
}
