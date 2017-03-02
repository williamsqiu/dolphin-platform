package com.canoo.dolphin.server.context;

import com.canoo.dolphin.server.ClientSessionExecutor;
import com.canoo.dolphin.util.Assert;
import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public class ClientSessionExecutorImpl implements ClientSessionExecutor {

    private final Executor runLaterExecutor;

    public ClientSessionExecutorImpl(final Executor runLaterExecutor) {
        this.runLaterExecutor = Assert.requireNonNull(runLaterExecutor, "runLaterExecutor");
    }

    @Override
    public Future<Void> runLaterInClientSession(final Runnable task) {
        Assert.requireNonNull(task, "task");
        final SettableFuture<Void> future = SettableFuture.create();
        runLaterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                    future.set(null);
                } catch (Exception e) {
                    future.setException(e);
                }
            }
        });
        return future;
    }
}
