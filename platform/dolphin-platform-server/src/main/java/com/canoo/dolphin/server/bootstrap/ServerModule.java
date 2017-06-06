package com.canoo.dolphin.server.bootstrap;

import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;

public interface ServerModule {

    String getName();

    int getOrder();

    boolean shouldBoot(DolphinPlatformConfiguration configuration);

    void initialize(final ServerCoreComponents coreComponents) throws InitializationException;
}
