package com.canoo.dolphin.client;

import com.canoo.platform.client.Toolkit;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by hendrikebbers on 12.10.17.
 */
public class TestToolkit implements Toolkit {

    private final static Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public Executor getUiExecutor() {
        return executor;
    }
}
