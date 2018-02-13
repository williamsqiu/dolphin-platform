package com.canoo.platform.core.timing;

import com.canoo.dp.impl.platform.core.Assert;

import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;

public interface Timing extends Serializable {

    String getName();

    default String getDescription() {
        return null;
    }

    default Duration getDuration() {
        final ZonedDateTime startTime = getStartTime();
        Assert.requireNonNull(startTime, "startTime");
        final ZonedDateTime endTime = getEndTime();
        if(endTime != null) {
            return Duration.between(startTime, endTime);
        } else {
            return Duration.between(startTime, ZonedDateTime.now());
        }
    }

    default boolean isDone() {
        return getEndTime() != null;
    }

    ZonedDateTime getStartTime();

    ZonedDateTime getEndTime();

}
