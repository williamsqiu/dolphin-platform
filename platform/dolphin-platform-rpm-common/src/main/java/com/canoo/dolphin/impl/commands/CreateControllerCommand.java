package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformRemotingConstants;
import com.canoo.impl.platform.core.Assert;
import org.opendolphin.core.comm.Command;

public final class CreateControllerCommand extends Command {

    private String parentControllerId;

    private String controllerName;

    public CreateControllerCommand() {
        super(PlatformRemotingConstants.CREATE_CONTROLLER_COMMAND_NAME);
    }

    public String getParentControllerId() {
        return parentControllerId;
    }

    public void setParentControllerId(final String parentControllerId) {
        this.parentControllerId = parentControllerId;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(final String controllerName) {
        Assert.requireNonBlank(controllerName, "controllerName");
        this.controllerName = controllerName;
    }
}
