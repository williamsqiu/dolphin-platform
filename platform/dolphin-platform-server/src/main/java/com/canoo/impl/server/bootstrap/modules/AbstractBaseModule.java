package com.canoo.impl.server.bootstrap.modules;

import com.canoo.impl.server.config.PlatformConfiguration;
import com.canoo.platform.server.spi.ServerModule;

import java.util.Collections;
import java.util.List;

public abstract class AbstractBaseModule implements ServerModule {

    @Override
    public List<String> getModuleDependencies() {
        return Collections.emptyList();
    }

    @Override
    public boolean shouldBoot(PlatformConfiguration configuration) {
        return configuration.getBooleanProperty(getActivePropertyName(), true);
    }

    protected abstract String getActivePropertyName();
}
