package com.canoo.dp.impl.platform.client.session;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.PlatformConstants;
import com.canoo.platform.client.http.HttpURLConnectionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;

public class ClientSessionSupportingURLConnectionRequestHandler implements HttpURLConnectionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ClientSessionSupportingURLConnectionRequestHandler.class);

    private final ClientSessionStoreImpl clientSessionStore;

    public ClientSessionSupportingURLConnectionRequestHandler(final ClientSessionStoreImpl clientSessionStore) {
        this.clientSessionStore = Assert.requireNonNull(clientSessionStore, "clientSessionStore");
    }

    @Override
    public void handle(final HttpURLConnection request) {
        Assert.requireNonNull(request, "request");
        final String clientId = clientSessionStore.getClientIdentifierForUrl(request.getURL());
        if (clientId != null) {
            LOG.debug("Adding client id {} to http request at {}", clientId, request.getURL());
            request.setRequestProperty(PlatformConstants.CLIENT_ID_HTTP_HEADER_NAME, clientId);
        } else {
            LOG.debug("Sending request to application at {} without client id. PlatformClient id not defined until now.", request.getURL());
        }
    }
}
