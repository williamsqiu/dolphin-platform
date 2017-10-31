package com.canoo.platform.remoting.server.event;

import org.apiguardian.api.API;

import java.io.Serializable;
import java.util.Map;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "1.0.0.RC1", status = EXPERIMENTAL)
public interface MessageEventContext<T extends Serializable> extends Serializable {
    
    Topic<T> getTopic();
    
    Map<String, Serializable> getMetadata();
    
    long getTimestamp();
}
