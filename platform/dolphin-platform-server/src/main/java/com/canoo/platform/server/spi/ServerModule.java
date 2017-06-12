package com.canoo.platform.server.spi;

import java.util.List;

public interface ServerModule {

    List<String> getModuleDependencies();

    boolean shouldBoot(PlatformConfiguration configuration);

    void initialize(final ServerCoreComponents coreComponents) throws ModuleInitializationException;
}
