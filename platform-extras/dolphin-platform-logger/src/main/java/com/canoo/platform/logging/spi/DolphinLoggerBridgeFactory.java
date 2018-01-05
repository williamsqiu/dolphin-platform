package com.canoo.platform.logging.spi;

import com.canoo.platform.logging.DolphinLoggerConfiguration;

public interface DolphinLoggerBridgeFactory {

    String getName();

    DolphinLoggerBridge create(DolphinLoggerConfiguration configuration);

}
