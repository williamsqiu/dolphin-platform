/*
 * Copyright 2015-2018 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dp.impl.platform.core;

import com.canoo.platform.core.PlatformThreadFactory;
import org.apiguardian.api.API;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class SimpleDolphinPlatformThreadFactory implements PlatformThreadFactory {

    private final static String NAME_PREFIX = "Dolphin-Platform-Background-Thread-";

    private final static String GROUP_NAME = "Dolphin Platform executors";

    private final AtomicInteger threadNumber = new AtomicInteger(0);

    private final Lock uncaughtExceptionHandlerLock = new ReentrantLock();

    private final ThreadGroup group;

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public SimpleDolphinPlatformThreadFactory(final Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = Assert.requireNonNull(uncaughtExceptionHandler, "uncaughtExceptionHandler");
        this.group = new ThreadGroup(GROUP_NAME);
    }

    public SimpleDolphinPlatformThreadFactory() {
        this(new SimpleUncaughtExceptionHandler());
    }

    @Override
    public Thread newThread(final Runnable task) {
        Assert.requireNonNull(task, "task");
        final Thread thread = AccessController.doPrivileged((PrivilegedAction<Thread>) () -> {
            final Thread backgroundThread = new Thread(group, task);
            backgroundThread.setName(NAME_PREFIX + threadNumber.getAndIncrement());
            backgroundThread.setDaemon(false);
            uncaughtExceptionHandlerLock.lock();
            try {
                backgroundThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            } finally {
                uncaughtExceptionHandlerLock.unlock();
            }

            return backgroundThread;
        });
        return thread;
    }

    public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        Assert.requireNonNull(uncaughtExceptionHandler, "uncaughtExceptionHandler");
        uncaughtExceptionHandlerLock.lock();
        try {
            this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        } finally {
            uncaughtExceptionHandlerLock.unlock();
        }
    }
}
