package com.canoo.dp.impl.platform.core.context;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.context.Context;

public class ContextImpl implements Context {

    private final String key;

    private final String value;

    public ContextImpl(final String key, final String value) {
        this.key = Assert.requireNonBlank(key, "key");
        this.value = value;
    }

    @Override
    public String getType() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
