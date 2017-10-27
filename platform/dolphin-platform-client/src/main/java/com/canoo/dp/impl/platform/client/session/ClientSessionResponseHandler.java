package com.canoo.dp.impl.platform.client.session;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.PlatformConstants;
import com.canoo.platform.core.http.HttpURLConnectionHandler;
import com.canoo.platform.client.session.ClientSessionStore;
import org.apiguardian.api.API;

import java.net.HttpURLConnection;

import static org.apiguardian.api.API.Status.INTERNAL;

/**
 * Created by hendrikebbers on 19.09.17.
 */
@API(since = "0.x", status = INTERNAL)
public class ClientSessionResponseHandler implements HttpURLConnectionHandler {

    private final ClientSessionStore clientSessionStore;

    public ClientSessionResponseHandler(final ClientSessionStore clientSessionStore) {
        this.clientSessionStore = Assert.requireNonNull(clientSessionStore, "clientSessionStore");
    }

    @Override
    public void handle(final HttpURLConnection response) {
        Assert.requireNonNull(response, "response");
        String clientIdInHeader = response.getHeaderField(PlatformConstants.CLIENT_ID_HTTP_HEADER_NAME);
        clientSessionStore.setClientIdentifierForUrl(response.getURL(), clientIdInHeader);
    }
}
