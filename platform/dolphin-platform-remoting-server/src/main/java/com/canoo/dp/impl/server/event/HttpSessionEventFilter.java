package com.canoo.dp.impl.server.event;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.remoting.server.event.EventFilter;
import com.canoo.platform.remoting.server.event.MessageEventContext;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpSessionEventFilter implements EventFilter {

    private final List<String> sessionIds;

    public HttpSessionEventFilter(String... sessionIds) {
        this.sessionIds = Arrays.asList(sessionIds);
    }

    @Override
    public boolean shouldHandleEvent(final MessageEventContext context) {
        Assert.requireNonNull(context, "context");

        final Map<String, Serializable> metadata = context.getMetadata();
        if(metadata == null) {
            return false;
        }

        final Object typeValue = metadata.get(EventConstants.TYPE_PARAM);
        if(typeValue == null || !typeValue.equals(EventConstants.TYPE_PLATFORM)) {
            return false;
        }

        final Object httpSessionIdValue = metadata.get(EventConstants.HTTP_SESSION_PARAM);
        if(httpSessionIdValue == null || !sessionIds.contains(httpSessionIdValue)) {
            return false;
        }

        return true;
    }
}
