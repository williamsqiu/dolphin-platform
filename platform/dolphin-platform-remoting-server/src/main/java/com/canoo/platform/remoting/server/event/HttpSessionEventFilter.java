package com.canoo.platform.remoting.server.event;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.server.event.EventConstants;
import org.apiguardian.api.API;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class HttpSessionEventFilter<T extends Serializable> implements Predicate<MessageEventContext<T>> {

    private final List<String> sessionIds;

    public HttpSessionEventFilter(String... sessionIds) {
        this.sessionIds = Arrays.asList(sessionIds);
    }

    @Override
    public boolean test(final MessageEventContext<T> context) {
        Assert.requireNonNull(context, "context");

        final Map<String, Serializable> metadata = context.getMetadata();
        if(metadata == null) {
            return false;
        }

        final Object httpSessionIdValue = metadata.get(EventConstants.HTTP_SESSION_PARAM);
        if(httpSessionIdValue == null || !sessionIds.contains(httpSessionIdValue)) {
            return false;
        }

        return true;
    }
}
