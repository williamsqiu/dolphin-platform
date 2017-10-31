package com.canoo.dp.impl.server.event;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.remoting.server.event.MessageEventContext;
import com.canoo.platform.remoting.server.event.Topic;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DolphinEventContext<T extends Serializable> implements MessageEventContext<T> {

    private final Topic<T> topic;

    private final long timestamp;

    private Map<String, Serializable> metadata = new HashMap<>();

    public DolphinEventContext(final Topic<T> topic, final long timestamp) {
        this.topic = Assert.requireNonNull(topic, "topic");
        this.timestamp = timestamp;
    }

    public void addMetadata(final String key, final Serializable value) {
        metadata.put(key, value);
    }

    @Override
    public Topic<T> getTopic() {
        return topic;
    }

    @Override
    public Map<String, Serializable> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
}
