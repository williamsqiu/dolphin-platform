package com.canoo.dolphin.integration;

import com.canoo.platform.client.Toolkit;

import java.util.concurrent.Executor;

public class IntegrationTestToolkit implements Toolkit {

    private final static String NAME = "integration test toolkit";


    @Override
    public Executor getUiExecutor() {
        return new Executor() {
            @Override
            public void execute(Runnable command) {
                command.run();
            }
        };
    }

    @Override
    public String getName() {
        return NAME;
    }
}
