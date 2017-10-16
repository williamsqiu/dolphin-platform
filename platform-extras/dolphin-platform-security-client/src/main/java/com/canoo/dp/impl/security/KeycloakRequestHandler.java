package com.canoo.dp.impl.security;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.security.Security;
import com.canoo.platform.core.http.HttpURLConnectionHandler;

import java.net.HttpURLConnection;

public class KeycloakRequestHandler implements HttpURLConnectionHandler {

    private final Security security;

    public KeycloakRequestHandler(final Security security) {
        this.security = Assert.requireNonNull(security, "security");
    }

    @Override
    public void handle(HttpURLConnection connection) {
        String accessToken = security.getAccessToken();
        if(accessToken != null && !accessToken.isEmpty()) {
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        }
    }
}
