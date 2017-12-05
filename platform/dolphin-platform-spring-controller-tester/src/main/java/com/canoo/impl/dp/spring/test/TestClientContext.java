package com.canoo.impl.dp.spring.test;

import com.canoo.platform.remoting.client.ClientContext;

import java.util.concurrent.ExecutionException;

/**
 * Created by hendrikebbers on 05.12.17.
 */
public interface TestClientContext extends ClientContext {

    void sendPing();

}
