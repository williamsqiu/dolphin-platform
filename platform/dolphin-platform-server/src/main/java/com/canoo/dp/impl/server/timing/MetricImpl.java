package com.canoo.dp.impl.server.timing;

import com.canoo.platform.server.timing.Metric;

import java.time.Duration;
import java.time.ZonedDateTime;

public class MetricImpl implements Metric {

    private final String name;

    private final String description;

    private final ZonedDateTime startTime;

    private Duration duration;

    public MetricImpl(final String name, final String description) {
        this.name = name;
        this.description = description;
        this.startTime = ZonedDateTime.now();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    @Override
    public void stop() {
        if(duration != null) {
            throw new IllegalStateException("Metric '" + name + "' was already stopped!");
        }
        duration = Duration.between(startTime, ZonedDateTime.now());
    }
}
