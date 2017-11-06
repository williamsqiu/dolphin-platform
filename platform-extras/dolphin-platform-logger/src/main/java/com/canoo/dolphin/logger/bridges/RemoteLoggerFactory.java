package com.canoo.dolphin.logger.bridges;

import com.canoo.dolphin.logger.DolphinLoggerConfiguration;
import com.canoo.dolphin.logger.spi.DolphinLoggerBridge;
import com.canoo.dolphin.logger.spi.DolphinLoggerBridgeFactory;

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
