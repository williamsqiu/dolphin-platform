package com.canoo.platform.logger.server.util;

import org.slf4j.event.Level;

import java.time.ZonedDateTime;
import java.util.Set;

public class LoggerSearchRequest {

    private final ZonedDateTime startDate;

    private final ZonedDateTime endDateTime;

    private final Set<Level> level;

    private final int maxResults;

    public LoggerSearchRequest(ZonedDateTime startDate, ZonedDateTime endDateTime, Set<Level> level, int maxResults) {
        this.startDate = startDate;
        this.endDateTime = endDateTime;
        this.level = level;
        this.maxResults = maxResults;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public Set<Level> getLevel() {
        return level;
    }
}
