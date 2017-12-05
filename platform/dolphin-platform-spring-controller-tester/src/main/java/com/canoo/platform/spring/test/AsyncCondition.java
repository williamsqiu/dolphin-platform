package com.canoo.platform.spring.test;

import org.apiguardian.api.API;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "1.0.0-RC2", status = EXPERIMENTAL)
public interface AsyncCondition {

    @Deprecated
    void await() throws InterruptedException;

    void await(long time, TimeUnit unit) throws InterruptedException, TimeoutException;

    void signal();

}
