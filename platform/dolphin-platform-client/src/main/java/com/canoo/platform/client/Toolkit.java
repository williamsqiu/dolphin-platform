package com.canoo.platform.client;

import com.canoo.platform.core.framework.Incubating;

import java.util.concurrent.Executor;

@Incubating("0.19.0")
public interface Toolkit {

    Executor getUiExecutor();

}
