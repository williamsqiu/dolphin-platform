package com.canoo.dolphin.server.event.impl;

import com.canoo.dolphin.server.event.EventSessionFilter;
import com.canoo.dolphin.server.event.Message;
import com.canoo.dolphin.server.event.Topic;
import com.canoo.dolphin.util.Assert;

import java.io.Serializable;

public class DolphinEvent<T extends Serializable> implements Serializable {

    private final Message<T> message;

    private final String senderSessionId;

    private final EventSessionFilter sessionFilter;

    public DolphinEvent(final String senderSessionId, final Message<T> message, final EventSessionFilter sessionFilter) {
        this.senderSessionId = senderSessionId;
        this.message = Assert.requireNonNull(message, "message");
        this.sessionFilter = Assert.requireNonNull(sessionFilter, "sessionFilter");
    }

    public Message<T> getMessage() {
        return message;
    }

    public Topic<T> getTopic() {
        return getMessage().getTopic();
    }

    public String getSenderSessionId() {
        return senderSessionId;
    }
}
