package com.canoo.impl.dp.logging.bridges;

import com.canoo.platform.logging.DolphinLoggerConfiguration;
import com.canoo.platform.logging.spi.DolphinLoggerBridge;
import com.canoo.platform.logging.spi.DolphinLoggerBridgeFactory;

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
