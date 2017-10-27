package com.canoo.dp.impl.platform.client.http.cookie;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.http.HttpURLConnectionHandler;
import com.canoo.platform.core.DolphinRuntimeException;
import org.apiguardian.api.API;

import java.net.HttpURLConnection;
import java.net.URISyntaxException;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class CookieRequestHandler implements HttpURLConnectionHandler {

    private final HttpClientCookieHandler clientCookieHandler;

    public CookieRequestHandler(final HttpClientCookieHandler clientCookieHandler) {
        this.clientCookieHandler = Assert.requireNonNull(clientCookieHandler, "clientCookieHandler");
    }

    @Override
    public void handle(final HttpURLConnection connection) {
        Assert.requireNonNull(connection, "connection");
        try {
            clientCookieHandler.setRequestCookies(connection);
        } catch (URISyntaxException e) {
            throw new DolphinRuntimeException("Can not set cookies", e);
        }
    }
}
