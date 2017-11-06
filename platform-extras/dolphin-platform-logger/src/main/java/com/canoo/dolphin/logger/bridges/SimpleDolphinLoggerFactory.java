package com.canoo.dolphin.logger.bridges;

import com.canoo.dolphin.logger.DolphinLoggerConfiguration;
import com.canoo.dolphin.logger.spi.DolphinLoggerBridge;
import com.canoo.dolphin.logger.spi.DolphinLoggerBridgeFactory;

public class SimpleDolphinLoggerFactory implements DolphinLoggerBridgeFactory {

    @Override
    public String getName() {
        return "SIMPLE";
    }

    @Override
    public DolphinLoggerBridge create(final DolphinLoggerConfiguration configuration) {
        return new SimpleDolphinLogger(configuration);
    }
}
