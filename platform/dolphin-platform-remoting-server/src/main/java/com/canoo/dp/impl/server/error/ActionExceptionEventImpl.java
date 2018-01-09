package com.canoo.dp.impl.server.error;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.remoting.server.error.ActionExceptionEvent;

public class ActionExceptionEventImpl<T extends Throwable> implements ActionExceptionEvent<T> {

    private final T throwable;

    private final String actionName;

    private final String controllerName;

    private boolean aborted = false;

    public ActionExceptionEventImpl(final String actionName, final String controllerName, final T throwable) {
        this.actionName = Assert.requireNonBlank(actionName, "actionName");
        this.controllerName = Assert.requireNonBlank(controllerName, "controllerName");
        this.throwable = Assert.requireNonNull(throwable, "throwable");
    }

    @Override
    public String getActionName() {
        return actionName;
    }

    @Override
    public String getControllerName() {
        return null;
    }

    @Override
    public T getException() {
        return throwable;
    }

    @Override
    public void abort() {
        this.aborted = true;
    }

    public boolean isAborted() {
        return aborted;
    }
}
