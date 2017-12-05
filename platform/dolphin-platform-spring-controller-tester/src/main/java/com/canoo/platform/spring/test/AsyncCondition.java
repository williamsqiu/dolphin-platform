package com.canoo.platform.spring.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface AsyncCondition {

    void await() throws InterruptedException;

    void await(long time, TimeUnit unit) throws InterruptedException, TimeoutException;

    void signal();

}
