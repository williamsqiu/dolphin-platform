package com.canoo.platform.remoting.server.event;

import org.apiguardian.api.API;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "1.0.0.RC1", status = EXPERIMENTAL)
public interface MessageEventContext<T extends Serializable> extends Serializable {
    
    Topic<T> getTopic();

    default Optional<Serializable> metadata(final String key) {
        return Optional.ofNullable(getMetadata().get(key));
    }

    Map<String, Serializable> getMetadata();
    
    long getTimestamp();
}
