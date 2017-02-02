package com.canoo.dolphin.server.event.impl;

import com.canoo.dolphin.server.event.EventSessionFilter;

public class DefaultEventSessionFilter implements EventSessionFilter {

    public static final EventSessionFilter INSTANCE = new DefaultEventSessionFilter();

    private DefaultEventSessionFilter() {}

    @Override
    public boolean shouldHandleEvent(String sessionId) {
        return true;
    }

    public static EventSessionFilter getInstance() {
        return INSTANCE;
    }
}
