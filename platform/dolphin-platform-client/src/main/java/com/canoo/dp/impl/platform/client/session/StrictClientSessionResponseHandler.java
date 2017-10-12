package com.canoo.dp.impl.platform.client.session;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.PlatformConstants;
import com.canoo.platform.client.http.HttpURLConnectionHandler;

import java.net.HttpURLConnection;
import java.net.URL;

public class StrictClientSessionResponseHandler implements HttpURLConnectionHandler {

    private final URL url;

    public StrictClientSessionResponseHandler(final URL url) {
        this.url = Assert.requireNonNull(url, "url");
    }
    @Override
    public void handle(final HttpURLConnection response) {
        Assert.requireNonNull(response, "response");
        if(this.url.equals(response.getURL())) {
            String clientIdInHeader = response.getHeaderField(PlatformConstants.CLIENT_ID_HTTP_HEADER_NAME);
            if (clientIdInHeader == null) {
                throw new RuntimeException("No client id found in response");
            }
        }
    }
}
