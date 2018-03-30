package com.canoo.dp.impl.platform.core.context;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.context.Context;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ContextImpl context = (ContextImpl) o;
        return Objects.equals(key, context.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
