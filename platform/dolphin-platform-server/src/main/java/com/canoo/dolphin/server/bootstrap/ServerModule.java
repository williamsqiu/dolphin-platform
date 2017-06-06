package com.canoo.dolphin.server.bootstrap;

import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;

public interface ServerModule {

    int DEFAULT_ORDER = 0;

    String getName();

    int getOrder();

    boolean shouldBoot(DolphinPlatformConfiguration configuration);

    void initialize(final ServerCoreComponents coreComponents) throws InitializationException;
}
