package com.canoo.platform.server.spi;

import com.canoo.impl.platform.core.Assert;

import java.util.Collections;
import java.util.List;

public abstract class AbstractBaseModule implements ServerModule {

    @Override
    public List<String> getModuleDependencies() {
        return Collections.emptyList();
    }

    @Override
    public boolean shouldBoot(PlatformConfiguration configuration) {
        return Assert.requireNonNull(configuration, "configuration").getBooleanProperty(getActivePropertyName(), true);
    }

    protected abstract String getActivePropertyName();
}
