package com.canoo.platform.client;

import com.canoo.platform.core.framework.Incubating;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Incubating("0.19.0")
public class HeadlessToolkit implements Toolkit {
    private final static Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public Executor getUiExecutor() {
        return executor;
    }
}
