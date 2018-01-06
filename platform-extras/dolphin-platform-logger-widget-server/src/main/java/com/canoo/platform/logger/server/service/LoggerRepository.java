package com.canoo.platform.logger.server.service;

import com.canoo.platform.logging.spi.LogMessage;
import com.canoo.platform.logger.model.LoggerSearchRequest;

import java.util.stream.Stream;

public interface LoggerRepository {

    Stream<LogMessage> search(LoggerSearchRequest searchRequest);
}
