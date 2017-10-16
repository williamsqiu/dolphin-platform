package com.canoo.platform.client;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HeadlessToolkit implements Toolkit {
    private final static Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public Executor getUiExecutor() {
        return executor;
    }
}
