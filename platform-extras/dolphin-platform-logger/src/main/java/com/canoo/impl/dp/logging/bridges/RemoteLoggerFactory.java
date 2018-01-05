package com.canoo.impl.dp.logging.bridges;

import com.canoo.platform.logging.DolphinLoggerConfiguration;
import com.canoo.platform.logging.spi.DolphinLoggerBridge;
import com.canoo.platform.logging.spi.DolphinLoggerBridgeFactory;

public class RemoteLoggerFactory implements DolphinLoggerBridgeFactory {

    @Override
    public String getName() {
        return "REMOTE";
    }

    @Override
    public DolphinLoggerBridge create(DolphinLoggerConfiguration configuration) {
        if(configuration.getRemoteUrl() != null) {
            return new RemoteLogger(configuration);
        }
        return null;
    }
}
