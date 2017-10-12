package com.canoo.platform.client.session;

import java.net.URL;

public interface ClientSessionStore {

    void resetSession(final URL url);

    String getClientIdentifierForUrl(URL endpoint);

    void setClientIdentifierForUrl(URL url, String clientIdInHeader);
}
