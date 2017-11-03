package com.canoo.dp.impl.server.event;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.remoting.server.event.EventFilter;
import com.canoo.platform.remoting.server.event.MessageListener;
import org.apiguardian.api.API;

import java.io.Serializable;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class ListenerWithFilter<T extends Serializable> {

    private final MessageListener<T> listener;

    private final EventFilter<T> filter;

    public ListenerWithFilter(MessageListener<T> listener, EventFilter<T> filter) {
        this.listener = Assert.requireNonNull(listener, "listener");
        this.filter = filter;
    }

    public ListenerWithFilter(MessageListener<T> listener) {
        this(listener, null);
    }

    public MessageListener<T> getListener() {
        return listener;
    }

    public EventFilter<T> getFilter() {
        return filter;
    }
}
