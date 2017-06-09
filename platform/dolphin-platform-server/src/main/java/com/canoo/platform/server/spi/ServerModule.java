package com.canoo.platform.server.spi;

import com.canoo.impl.server.bootstrap.ServerCoreComponents;
import com.canoo.impl.server.config.PlatformConfiguration;

import java.util.List;

public interface ServerModule {

    List<String> getModuleDependencies();

    boolean shouldBoot(PlatformConfiguration configuration);

    void initialize(final ServerCoreComponents coreComponents) throws ModuleInitializationException;
}
