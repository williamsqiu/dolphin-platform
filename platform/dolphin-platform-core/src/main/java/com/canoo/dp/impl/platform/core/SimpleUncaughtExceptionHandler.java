package com.canoo.dp.impl.platform.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        Assert.requireNonNull(thread, "thread");
        Assert.requireNonNull(exception, "exception");
        LOG.error("Unhandled error in Dolphin Platform background thread " + thread.getName(), exception);
    }
}
