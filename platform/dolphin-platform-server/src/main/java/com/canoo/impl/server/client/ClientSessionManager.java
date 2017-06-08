package com.canoo.impl.server.client;

import com.canoo.dolphin.util.Assert;
import com.canoo.impl.server.config.PlatformConfiguration;
import com.canoo.platform.server.client.ClientSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientSessionManager {

    private static final Logger LOG = LoggerFactory.getLogger(ClientSessionManager.class);

    private static final String DOLPHIN_CONTEXT_MAP = "DOLPHIN_CONTEXT_MAP";

    private static final String CLIENT_SESSION_LOCK = "CLIENT_SESSION_LOCK";

    private final PlatformConfiguration configuration;

    private final ClientSessionLifecycleHandlerImpl lifecycleHandler;

    public ClientSessionManager(PlatformConfiguration configuration, ClientSessionLifecycleHandlerImpl lifecycleHandler) {
        this.configuration = configuration;
        this.lifecycleHandler = lifecycleHandler;
    }

    public String createClientSession(HttpSession httpSession) throws MaxSessionCountReachedException {
        if (!canAddInSession(httpSession)) {
            throw new MaxSessionCountReachedException();
        }

        final ClientSession clientSession = new ClientSessionImpl();
        add(httpSession, clientSession);

        lifecycleHandler.onSessionCreated(clientSession);
        LOG.trace("Created new DolphinContext {} in http session {}", clientSession.getId(), httpSession.getId());
        return clientSession.getId();
    }

    public boolean checkValidClientSession(HttpSession httpSession, String clientSessionId) {
        Lock lock = getOrCreateClientSessionLockForHttpSession(httpSession);
        lock.lock();
        try {
            return getOrCreateClientSessionMapInHttpSession(httpSession).containsKey(clientSessionId);
        } finally {
            lock.unlock();
        }
    }

    public void removeAllClientSessionsInHttpSession(HttpSession httpSession) {
        Lock lock = getOrCreateClientSessionLockForHttpSession(httpSession);
        lock.lock();
        try {
            Map<String, ClientSession> map = getOrCreateClientSessionMapInHttpSession(httpSession);
            for(ClientSession session : map.values()) {
                lifecycleHandler.onSessionDestroyed(session);
            }
            map.clear();
        } finally {
            lock.unlock();
        }
    }

    public void setClientSessionForThread(HttpSession httpSession, String clientSessionId) {
        Lock lock = getOrCreateClientSessionLockForHttpSession(httpSession);
        lock.lock();
        try {
            lifecycleHandler.setCurrentSession(getOrCreateClientSessionMapInHttpSession(httpSession).get(clientSessionId));
        } finally {
            lock.unlock();
        }
    }

    public void resetClientSessionForThread() {
        lifecycleHandler.setCurrentSession(null);
    }

    private void add(HttpSession httpSession, ClientSession clientSession) {
        Lock lock = getOrCreateClientSessionLockForHttpSession(httpSession);
        lock.lock();
        try {
            getOrCreateClientSessionMapInHttpSession(httpSession).put(clientSession.getId(), clientSession);
        } finally {
            lock.unlock();
        }
    }

    private boolean canAddInSession(HttpSession httpSession) {
        Lock lock = getOrCreateClientSessionLockForHttpSession(httpSession);
        lock.lock();
        try {
            return getOrCreateClientSessionMapInHttpSession(httpSession).size() < configuration.getMaxClientsPerSession();
        } finally {
            lock.unlock();
        }
    }

    private Map<String, ClientSession> getOrCreateClientSessionMapInHttpSession(HttpSession session) {
        Assert.requireNonNull(session, "session");
        if (session.getAttribute(DOLPHIN_CONTEXT_MAP) == null) {
            session.setAttribute(DOLPHIN_CONTEXT_MAP, new HashMap<>());
        }
        return (Map<String, ClientSession>) session.getAttribute(DOLPHIN_CONTEXT_MAP);
    }

    private synchronized Lock getOrCreateClientSessionLockForHttpSession(HttpSession session) {
        Assert.requireNonNull(session, "session");
        if (session.getAttribute(CLIENT_SESSION_LOCK) == null) {
            session.setAttribute(CLIENT_SESSION_LOCK, new ReentrantLock());
        }
        return (Lock) session.getAttribute(CLIENT_SESSION_LOCK);
    }

}
