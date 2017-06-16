package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformRemotingConstants;
import com.canoo.impl.platform.core.Assert;
import org.opendolphin.core.comm.Command;

import java.util.HashMap;
import java.util.Map;

public final class CallActionCommand extends Command {

    private String controllerId;

    private String actionName;

    private Map<String, Object> params = new HashMap<>();

    public CallActionCommand() {
        super(PlatformRemotingConstants.CALL_ACTION_COMMAND_NAME);
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(final String controllerId) {
        Assert.requireNonBlank(controllerId, "controllerId");
        this.controllerId = controllerId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(final String actionName) {
        Assert.requireNonBlank(actionName, "actionName");
        this.actionName = actionName;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void addParam(final String name, final Object value) {
        Assert.requireNonBlank(name, "name");
        params.put(name, value);
    }
}

