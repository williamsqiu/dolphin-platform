package com.canoo.impl.server.servlet;

import com.canoo.dolphin.util.Assert;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class Mutex implements Serializable {

    public <T> T sync(final Callable<T> callable) throws Exception {
        Assert.requireNonNull(callable, "callable");
        synchronized (this) {
            return callable.call();
        }
    }

    public void sync(final Runnable runnable) throws Exception {
        Assert.requireNonNull(runnable, "runnable");
        sync(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                runnable.run();
                return null;
            }
        });
    }

}
