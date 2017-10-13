package com.canoo.dolphin.integration;

import com.canoo.platform.client.Toolkit;

import java.util.concurrent.Executor;

/**
 * Created by hendrikebbers on 12.10.17.
 */
public class IntegrationTestToolkit implements Toolkit {

    @Override
    public Executor getUiExecutor() {
        return new Executor() {
            @Override
            public void execute(Runnable command) {
                command.run();
            }
        };
    }
}
