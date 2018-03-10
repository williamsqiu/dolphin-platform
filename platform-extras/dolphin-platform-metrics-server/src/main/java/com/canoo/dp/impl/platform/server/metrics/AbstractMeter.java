package com.canoo.dp.impl.platform.server.metrics;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.metrics.Meter;
import com.canoo.platform.metrics.MeterTag;

import java.util.Collections;
import java.util.List;

public class AbstractMeter implements Meter {

    private final String name;

    private final List<MeterTag> tags;

    public AbstractMeter(final String name, final List<MeterTag> tags) {
        this.name = Assert.requireNonBlank(name, "name");
        Assert.requireNonNull(tags, "tags");
        this.tags = Collections.unmodifiableList(tags);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<MeterTag> getTags() {
        return tags;
    }

    @Override
    public void close() throws Exception {}
}
