package com.canoo.dolphin.server.context;

import com.canoo.dolphin.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * A {@link HttpSessionListener} that destroys all {@link DolphinContext} instances for a session
 */
public class DolphinContextHttpSessionListener implements HttpSessionListener {

    private static final Logger LOG = LoggerFactory.getLogger(DolphinContextHttpSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {}

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        Assert.requireNonNull(sessionEvent, "sessionEvent");
        LOG.trace("Session {} destroyed! Will remove all DolphinContext instances for the session.", sessionEvent.getSession().getId());
        DolphinContextUtils.removeAllContextsInSession(sessionEvent.getSession());
    }
}
