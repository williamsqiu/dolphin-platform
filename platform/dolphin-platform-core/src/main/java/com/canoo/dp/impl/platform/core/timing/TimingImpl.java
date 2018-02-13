package com.canoo.dp.impl.platform.core.timing;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.timing.Timing;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class TimingImpl implements Timing {

    private final String name;

    private final String id;

    private final String description;

    private final ZonedDateTime startTime;

    private ZonedDateTime endTime;

    public TimingImpl(final String name) {
        this(name, null, ZonedDateTime.now());
    }

    public TimingImpl(final String name, final String description) {
        this(name, description, ZonedDateTime.now());
    }

    public TimingImpl(final String name, final ZonedDateTime startTime) {
        this(name, null, startTime);
    }

    public TimingImpl(final String name, final String description, final ZonedDateTime startTime) {
        this.name = Assert.requireNonBlank(name, "name");
        this.description = description;
        this.startTime = Assert.requireNonNull(startTime, "startTime");
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    @Override
    public synchronized ZonedDateTime getEndTime() {
        return endTime;
    }

    public synchronized void setEndTime(final ZonedDateTime endTime) {
        if(this.endTime != null) {
            throw new IllegalStateException("EndTime already defined!");
        }
        this.endTime = Assert.requireNonNull(endTime, "endTime");
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TimingImpl timing = (TimingImpl) o;
        return Objects.equals(id, timing.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
