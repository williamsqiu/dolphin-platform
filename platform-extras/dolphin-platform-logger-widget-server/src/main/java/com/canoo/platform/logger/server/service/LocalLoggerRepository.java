package com.canoo.platform.logger.server.service;

import com.canoo.dolphin.logger.DolphinLoggerFactory;
import com.canoo.dolphin.logger.impl.LogMessage;
import com.canoo.platform.logger.server.util.LoggerSearchRequest;

import java.util.Comparator;
import java.util.stream.Stream;

public class LocalLoggerRepository implements LoggerRepository {

    @Override
    public Stream<LogMessage> search(LoggerSearchRequest searchRequest) {
        return DolphinLoggerFactory.getLogCache().stream().
                filter(l -> l.getTimestamp().isAfter(searchRequest.getStartDate())).
                filter(l -> l.getTimestamp().isBefore(searchRequest.getEndDateTime())).
                filter(l -> searchRequest.getLevel().contains(l.getLevel())).
                sorted(Comparator.comparing(LogMessage::getTimestamp)).
                limit(searchRequest.getMaxResults());
    }
}
