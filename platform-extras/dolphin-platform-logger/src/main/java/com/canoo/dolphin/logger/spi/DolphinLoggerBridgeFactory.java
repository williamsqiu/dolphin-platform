package com.canoo.dolphin.logger.spi;

import com.canoo.dolphin.logger.DolphinLoggerConfiguration;

public interface DolphinLoggerBridgeFactory {

    String getName();

    DolphinLoggerBridge create(DolphinLoggerConfiguration configuration);

}
