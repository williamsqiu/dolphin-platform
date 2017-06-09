package com.canoo.impl.server.client;

import com.canoo.dolphin.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * A {@link HttpSessionListener} that destroys all {@link DolphinContext} instances for a session
 */
public class HttpSessionCleanerListener implements HttpSessionListener {

    private static final Logger LOG = LoggerFactory.getLogger(HttpSessionCleanerListener.class);

    private final ClientSessionManager clientSessionManager;

    public HttpSessionCleanerListener(ClientSessionManager clientSessionManager) {
        this.clientSessionManager = clientSessionManager;
    }

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {}

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        Assert.requireNonNull(sessionEvent, "sessionEvent");
        LOG.trace("Http session {} destroyed! Will remove all client sessions for the http session.", sessionEvent.getSession().getId());
        clientSessionManager.removeAllClientSessionsInHttpSession(sessionEvent.getSession());
    }
}
