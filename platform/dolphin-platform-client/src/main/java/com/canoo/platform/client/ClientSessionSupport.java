package com.canoo.platform.client;

import java.net.URL;

public interface ClientSessionSupport extends HttpClient {

    void closeClientSession(final URL url);

    String getClientId(final URL url);
}
