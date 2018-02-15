package com.canoo.platform.server.timing;

import java.time.Duration;

/**
 * A metric that defines a single task on the server
 */
public interface Metric {

    /**
     * Returns the name of the metric
     * @return the name
     */
    String getName();

    /**
     * Returns the description of the metric
     * @return the description
     */
    String getDescription();

    /**
     * Returns the duration of the metric after {@link #stop()} was called. If {@link #stop()}
     * was not called {@code null} will be returned
     * @return the duration of the metric or null
     */
    Duration getDuration();

    /**
     *  Stops the metric and calculates the duration. This can only be called ones.
     * @throws IllegalStateException if {@link #stop()} was already called.
     */
    void stop() throws IllegalStateException;

}
