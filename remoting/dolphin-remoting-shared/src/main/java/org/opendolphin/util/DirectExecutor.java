package org.opendolphin.util;

import java.util.Objects;
import java.util.concurrent.Executor;

public class DirectExecutor implements Executor {

    private final static DirectExecutor INSTANCE = new DirectExecutor();

    private DirectExecutor() {
    }

    @Override
    public void execute(Runnable command) {
        Objects.requireNonNull(command).run();
    }

    public static DirectExecutor getInstance() {
        return INSTANCE;
    }
}
