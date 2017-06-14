package com.canoo.impl.server.client;

import com.canoo.impl.platform.core.Assert;
import com.canoo.impl.server.servlet.Mutex;
import com.canoo.platform.server.client.ClientSession;
import com.canoo.platform.server.client.ClientSessionListener;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ClientSessionMutextHolder implements ClientSessionListener {

    private final Map<String, WeakReference<Mutex>> sessionMutexMap = new HashMap<>();

    private final static String SESSION_MUTEX_ATTRIBUTE = "Session-Mutex";

    @Override
    public void sessionCreated(final ClientSession clientSession) {
        Assert.requireNonNull(clientSession, "clientSession");
        final Mutex mutex = new Mutex();
        clientSession.setAttribute(SESSION_MUTEX_ATTRIBUTE, mutex);
        sessionMutexMap.put(clientSession.getId(), new WeakReference<>(mutex));
    }

    @Override
    public void sessionDestroyed(final ClientSession clientSession) {
        Assert.requireNonNull(clientSession, "clientSession");
        sessionMutexMap.remove(clientSession.getId());
    }

    public Mutex getMutexForClientSession(final String sessionId) {
        Assert.requireNonBlank(sessionId, "sessionId");
        final WeakReference<Mutex> mutexReference = sessionMutexMap.get(sessionId);
        Assert.requireNonNull(mutexReference, "mutexReference");
        return mutexReference.get();
    }
}
