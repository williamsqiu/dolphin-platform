package com.canoo.dp.impl.server.event;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.remoting.server.event.MessageEventContext;
import com.canoo.platform.remoting.server.event.MessageListener;
import org.apiguardian.api.API;

import java.io.Serializable;
import java.util.function.Predicate;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class ListenerWithFilter<T extends Serializable> {

    private final MessageListener<T> listener;

    private final Predicate<MessageEventContext<T>> filter;

    public ListenerWithFilter(final MessageListener<T> listener, final Predicate<MessageEventContext<T>> filter) {
        this.listener = Assert.requireNonNull(listener, "listener");
        this.filter = filter;
    }

    public ListenerWithFilter(MessageListener<T> listener) {
        this(listener, null);
    }

    public MessageListener<T> getListener() {
        return listener;
    }

    public Predicate<MessageEventContext<T>> getFilter() {
        return filter;
    }
}
