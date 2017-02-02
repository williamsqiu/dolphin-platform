package com.canoo.dolphin.server.event;

import java.io.Serializable;

public interface EventSessionFilter extends Serializable {

    boolean shouldHandleEvent(String sessionId);

}
