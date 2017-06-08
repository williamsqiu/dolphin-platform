package com.canoo.dolphin.server.bootstrap;

import com.canoo.dolphin.server.config.PlatformConfiguration;

public interface ServerModule {

    int DEFAULT_ORDER = 0;

    String getName();

    int getOrder();

    boolean shouldBoot(PlatformConfiguration configuration);

    void initialize(final ServerCoreComponents coreComponents) throws InitializationException;
}
