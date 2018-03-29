package com.canoo.dp.impl.platform.server.metrics.servlet;

import com.canoo.dp.impl.platform.server.metrics.MetricsImpl;
import com.canoo.dp.impl.platform.server.metrics.TagImpl;
import com.canoo.platform.metrics.Tag;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicLong;

public class MetricsHttpSessionListener implements HttpSessionListener {

    private final AtomicLong counter = new AtomicLong();

    @Override
    public void sessionCreated(final HttpSessionEvent se) {
        final Tag idTag = new TagImpl("sessionId", se.getSession().getId());
        MetricsImpl.getInstance().getOrCreateGauge("httpSessions", idTag)
                .setValue(counter.incrementAndGet());
    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent se) {
        final Tag idTag = new TagImpl("sessionId", se.getSession().getId());
        MetricsImpl.getInstance().getOrCreateGauge("httpSessions", idTag)
                .setValue(counter.decrementAndGet());
    }
}
