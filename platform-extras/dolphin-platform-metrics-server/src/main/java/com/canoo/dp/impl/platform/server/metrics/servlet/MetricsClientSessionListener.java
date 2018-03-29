package com.canoo.dp.impl.platform.server.metrics.servlet;

import com.canoo.dp.impl.platform.server.metrics.MetricsImpl;
import com.canoo.dp.impl.platform.server.metrics.TagImpl;
import com.canoo.platform.metrics.Tag;
import com.canoo.platform.server.ServerListener;
import com.canoo.platform.server.client.ClientSession;
import com.canoo.platform.server.client.ClientSessionListener;

import java.util.concurrent.atomic.AtomicLong;

@ServerListener
public class MetricsClientSessionListener implements ClientSessionListener {

    private final AtomicLong counter = new AtomicLong();


    @Override
    public void sessionCreated(final ClientSession clientSession) {
        final Tag idTag = new TagImpl("sessionId", clientSession.getId());
        MetricsImpl.getInstance().getOrCreateGauge("clientSessions", idTag)
                .setValue(counter.incrementAndGet());
    }

    @Override
    public void sessionDestroyed(final ClientSession clientSession) {
        final Tag idTag = new TagImpl("sessionId", clientSession.getId());
        MetricsImpl.getInstance().getOrCreateGauge("clientSessions", idTag)
                .setValue(counter.incrementAndGet());
    }
}
