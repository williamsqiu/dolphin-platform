package com.canoo.platform.remoting.server.event;

import java.io.Serializable;
import java.util.Map;

public interface MessageEventContext<T extends Serializable> extends Serializable {
    
    Topic<T> getTopic();
    
    Map<String, Serializable> getMetadata();
    
    long getTimestamp();
}
