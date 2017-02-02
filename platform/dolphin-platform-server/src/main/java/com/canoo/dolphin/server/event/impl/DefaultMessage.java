package com.canoo.dolphin.server.event.impl;

import com.canoo.dolphin.server.event.Message;
import com.canoo.dolphin.server.event.Topic;
import com.canoo.dolphin.util.Assert;

import java.io.Serializable;

public class DefaultMessage<T extends Serializable> implements Message<T> {

    private final Topic<T> topic;

    private final T data;

    private final long timestamp;

    public DefaultMessage(final Topic<T> topic, final T data, final long timestamp) {
        this.topic = Assert.requireNonNull(topic, "topic");
        this.data = data;
        this.timestamp = timestamp;
    }

    @Override
    public Topic<T> getTopic() {
        return topic;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public long getSendTimestamp() {
        return timestamp;
    }
}
