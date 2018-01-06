package com.canoo.platform.logger.server.service;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.impl.dp.logging.DolphinLoggerFactory;
import com.canoo.platform.logging.spi.LogMessage;
import com.canoo.platform.logger.model.LoggerSearchRequest;

import java.util.Comparator;
import java.util.stream.Stream;

public class LocalLoggerRepository implements LoggerRepository {

    @Override
    public Stream<LogMessage> search(final LoggerSearchRequest searchRequest) {
        Assert.requireNonNull(searchRequest, "searchRequest");
        return DolphinLoggerFactory.getLogCache().stream().
                filter(l -> l.getTimestamp().isAfter(searchRequest.getStartDate())).
                filter(l -> l.getTimestamp().isBefore(searchRequest.getEndDateTime())).
                filter(l -> searchRequest.getLevel().contains(l.getLevel())).
                sorted(Comparator.comparing(LogMessage::getTimestamp)).
                limit(searchRequest.getMaxResults());
    }
}
