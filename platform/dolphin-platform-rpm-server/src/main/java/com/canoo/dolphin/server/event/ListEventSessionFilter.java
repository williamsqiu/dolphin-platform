package com.canoo.dolphin.server.event;

import java.util.Arrays;
import java.util.List;

public class ListEventSessionFilter implements EventSessionFilter {

    private final List<String> sessionIds;

    public ListEventSessionFilter(String... sessionIds) {
        this.sessionIds = Arrays.asList(sessionIds);
    }

    @Override
    public boolean shouldHandleEvent(String s) {
        return sessionIds.contains(s);
    }
}

