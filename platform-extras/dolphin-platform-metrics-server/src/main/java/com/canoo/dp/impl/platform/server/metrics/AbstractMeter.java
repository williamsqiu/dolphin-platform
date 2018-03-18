package com.canoo.dp.impl.platform.server.metrics;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.metrics.Metric;
import com.canoo.platform.metrics.Tag;

import java.util.List;
import java.util.Objects;

public abstract class AbstractMeter implements Metric {

    private final MeterIdentifier identifier;

    private final AutoCloseable closeable;

    public AbstractMeter(final MeterIdentifier identifier, final AutoCloseable closeable) {
        this.identifier = Assert.requireNonNull(identifier, "identifier");
        this.closeable = Assert.requireNonNull(closeable, "closeable");
    }

    @Override
    public String getName() {
        return identifier.getName();
    }

    @Override
    public List<Tag> getTags() {
        return identifier.getTags();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AbstractMeter that = (AbstractMeter) o;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public void close() throws Exception {
        closeable.close();
    }
}
