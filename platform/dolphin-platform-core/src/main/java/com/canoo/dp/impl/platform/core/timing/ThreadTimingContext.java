package com.canoo.dp.impl.platform.core.timing;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.timing.Timing;
import com.canoo.platform.core.timing.TimingContext;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadTimingContext implements TimingContext {

    private final Thread thread;

    private final String id;

    private final List<TimingImpl> timings;

    public ThreadTimingContext(final Thread thread) {
        this.thread = Assert.requireNonNull(thread, "thread");
        this.timings = new CopyOnWriteArrayList<>();
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getName() {
        return thread.getName();
    }

    @Override
    public String getDescription() {
        return "Timing context of thread " + thread.getName();
    }

    @Override
    public List<Timing> getTimings() {
        return Collections.unmodifiableList(timings);
    }

    public Timing start(final String name) {
        return start(name, null, ZonedDateTime.now());
    }

    public Timing start(final String name, ZonedDateTime zonedDateTime) {
        return start(name, null, zonedDateTime);
    }

    public Timing start(final String name, String description) {
        return start(name, description, ZonedDateTime.now());
    }

    public Timing start(final String name, String description, ZonedDateTime startTime) {
        TimingImpl timing = new TimingImpl(name, description, startTime);
        timings.add(timing);
        return timing;
    }

    public Timing stop(final Timing timing) {
        final ZonedDateTime time = ZonedDateTime.now();
        Assert.requireNonNull(timing, "timing");
        final TimingImpl toStop = timings.stream()
                .filter(t -> t.equals(timing))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can not find given timing"));
        toStop.setEndTime(time);
        return toStop;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ThreadTimingContext that = (ThreadTimingContext) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
