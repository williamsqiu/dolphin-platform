package com.canoo.platform.logger.server.service;

import com.canoo.dolphin.logger.impl.LogMessage;
import com.canoo.platform.logger.model.LoggerSearchRequest;

import java.util.stream.Stream;

public interface LoggerRepository {

    Stream<LogMessage> search(LoggerSearchRequest searchRequest);
}
