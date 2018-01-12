package com.canoo.dolphin.integration.action;

import com.canoo.platform.remoting.DolphinBean;
import com.canoo.platform.remoting.Property;

@DolphinBean
public class ActionErrorBean {

    private Property<String> actionName;

    private Property<String> controllerName;

    private Property<String> exceptionName;

    public String getActionName() {
        return actionName.get();
    }

    public Property<String> actionNameProperty() {
        return actionName;
    }

    public void setActionName(final String actionName) {
        this.actionName.set(actionName);
    }

    public String getControllerName() {
        return controllerName.get();
    }

    public Property<String> controllerNameProperty() {
        return controllerName;
    }

    public void setControllerName(final String controllerName) {
        this.controllerName.set(controllerName);
    }

    public String getExceptionName() {
        return exceptionName.get();
    }

    public Property<String> exceptionNameProperty() {
        return exceptionName;
    }

    public void setExceptionName(final String exceptionName) {
        this.exceptionName.set(exceptionName);
    }
}
