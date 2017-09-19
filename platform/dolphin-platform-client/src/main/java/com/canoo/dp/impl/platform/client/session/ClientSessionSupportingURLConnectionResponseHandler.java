package com.canoo.dp.impl.platform.client.session;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.PlatformConstants;
import com.canoo.platform.client.http.HttpURLConnectionHandler;

import java.net.HttpURLConnection;

/**
 * Created by hendrikebbers on 19.09.17.
 */
public class ClientSessionSupportingURLConnectionResponseHandler implements HttpURLConnectionHandler {

    private final ClientSessionStore clientSessionStore;

    public ClientSessionSupportingURLConnectionResponseHandler(final ClientSessionStore clientSessionStore) {
        this.clientSessionStore = Assert.requireNonNull(clientSessionStore, "clientSessionStore");
    }

    @Override
    public void handle(final HttpURLConnection response) {
        Assert.requireNonNull(response, "response");
        String clientIdInHeader = response.getHeaderField(PlatformConstants.CLIENT_ID_HTTP_HEADER_NAME);
        clientSessionStore.setClientIdentifierForUrl(response.getURL(), clientIdInHeader);
    }
}
