package com.canoo.dolphin.integration.server.runlater;

import com.canoo.dolphin.integration.runlater.RunLaterTestBean;
import com.canoo.platform.remoting.server.*;
import com.canoo.platform.remoting.server.RemotingModel;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.canoo.dolphin.integration.runlater.RunLaterTestConstants.RUN_LATER_ACTION_NAME;
import static com.canoo.dolphin.integration.runlater.RunLaterTestConstants.RUN_LATER_ASYNC_ACTION_NAME;
import static com.canoo.dolphin.integration.runlater.RunLaterTestConstants.RUN_LATER_CONTROLLER_NAME;

@RemotingController(RUN_LATER_CONTROLLER_NAME)
public class RunLaterTestController {

    private final AtomicInteger callIndex = new AtomicInteger();

    private final ClientSessionExecutor sessionExecutor;

    @RemotingModel
    private RunLaterTestBean model;

    @Inject
    public RunLaterTestController(final RemotingContext remotingContext) {
        this.sessionExecutor = remotingContext.createSessionExecutor();
    }

    @PostConstruct
    public void init() {
        resetModel();

        model.setPostConstructPreRunLaterCallIndex(callIndex.incrementAndGet());
        sessionExecutor.runLaterInClientSession(() -> model.setPostConstructRunLaterCallIndex(callIndex.incrementAndGet()));
        model.setPostConstructPostRunLaterCallIndex(callIndex.incrementAndGet());
    }

    @RemotingAction(RUN_LATER_ACTION_NAME)
    public void runLaterAction() {
        model.setActionPreRunLaterCallIndex(callIndex.incrementAndGet());
        sessionExecutor.runLaterInClientSession(() -> model.setActionRunLaterCallIndex(callIndex.incrementAndGet()));
        model.setActionPostRunLaterCallIndex(callIndex.incrementAndGet());
    }

    @RemotingAction(RUN_LATER_ASYNC_ACTION_NAME)
    public void runLaterAsyncAction() {
        model.setActionPreRunLaterAsyncCallIndex(callIndex.incrementAndGet());
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Error!", e);
            }
            sessionExecutor.runLaterInClientSession(() -> model.setActionRunLaterAsyncCallIndex(callIndex.incrementAndGet()));
        });
        model.setActionPostRunLaterAsyncCallIndex(callIndex.incrementAndGet());
    }


    private void resetModel() {
        callIndex.set(0);
        model.setPostConstructPreRunLaterCallIndex(-1);
        model.setPostConstructRunLaterCallIndex(-1);
        model.setPostConstructPostRunLaterCallIndex(-1);
        model.setActionPreRunLaterCallIndex(-1);
        model.setActionRunLaterCallIndex(-1);
        model.setActionPostRunLaterCallIndex(-1);
        model.setActionPreRunLaterAsyncCallIndex(-1);
        model.setActionRunLaterAsyncCallIndex(-1);
        model.setActionPostRunLaterAsyncCallIndex(-1);
    }
}
