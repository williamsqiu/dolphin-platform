package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformRemotingConstants;
import com.canoo.impl.platform.core.Assert;
import org.opendolphin.core.comm.Command;

public final class DestroyControllerCommand extends Command {

    private String controllerId;

    public DestroyControllerCommand() {
        super(PlatformRemotingConstants.DESTROY_CONTROLLER_COMMAND_NAME);
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(final String controllerId) {
        Assert.requireNonBlank(controllerId, "controllerId");
        this.controllerId = controllerId;
    }
}

