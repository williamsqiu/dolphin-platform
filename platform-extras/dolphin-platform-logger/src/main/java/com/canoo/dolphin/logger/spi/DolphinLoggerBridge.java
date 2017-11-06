package com.canoo.dolphin.logger.spi;

import com.canoo.dolphin.logger.impl.LogMessage;

public interface DolphinLoggerBridge {

    void log(LogMessage logMessage);

}
