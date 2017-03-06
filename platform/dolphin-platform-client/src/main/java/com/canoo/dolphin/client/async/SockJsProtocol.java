package com.canoo.dolphin.client.async;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.Executor;

public interface SockJsProtocol {

    SockJsSession connect(final URL baseEndpoint, String serverId, String sessionId, SockJsHandler handler, Executor executor) throws IOException, URISyntaxException;
}
