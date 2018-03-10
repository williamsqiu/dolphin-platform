package com.canoo.dp.impl.platform.server.metrics;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.metrics.Tag;

public class TagImpl implements Tag {

    private final String key;

    private final String value;

    public TagImpl(final String key, final String value) {
        this.key = Assert.requireNonBlank(key, "key");
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
