package com.canoo.impl.server.servlet;

import com.canoo.dolphin.util.Assert;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class HttpSessionMutexHolder implements HttpSessionListener {

    private final Map<String, WeakReference<Mutex>> sessionMutexMap = new HashMap<>();

    private final static String SESSION_MUTEX_ATTRIBUTE = "Session-Mutex";

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        Assert.requireNonNull(sessionEvent, "sessionEvent");
        HttpSession session = sessionEvent.getSession();
        Assert.requireNonNull(session, "session");
        final Mutex mutex = new Mutex();
        session.setAttribute(SESSION_MUTEX_ATTRIBUTE, mutex);
        sessionMutexMap.put(session.getId(), new WeakReference<>(mutex));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        Assert.requireNonNull(sessionEvent, "sessionEvent");
        HttpSession session = sessionEvent.getSession();
        Assert.requireNonNull(session, "session");
        sessionMutexMap.remove(session.getId());
    }

    public Mutex getMutexForHttpSession(final String sessionId) {
        Assert.requireNonBlank(sessionId, "sessionId");
        WeakReference<Mutex> mutexReference = sessionMutexMap.get(sessionId);
        Assert.requireNonNull(mutexReference, "mutexReference");
        return mutexReference.get();
    }
}
