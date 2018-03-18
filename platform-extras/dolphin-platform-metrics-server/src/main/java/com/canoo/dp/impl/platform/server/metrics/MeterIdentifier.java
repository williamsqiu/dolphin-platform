package com.canoo.dp.impl.platform.server.metrics;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.metrics.Tag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MeterIdentifier {

    private final String name;

    private final List<Tag> tags;

    public MeterIdentifier(final String name, final Tag... tags) {
        this.name = Assert.requireNonBlank(name, "name");
        Assert.requireNonNull(tags, "tags");
        this.tags = Collections.unmodifiableList(Arrays.asList(tags));
    }

    public String getName() {
        return name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MeterIdentifier that = (MeterIdentifier) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tags);
    }
}
