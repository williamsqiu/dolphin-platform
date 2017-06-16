package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformRemotingConstants;
import org.opendolphin.core.comm.Command;

public final class DestroyControllerCommand extends Command {

    public DestroyControllerCommand() {
        super(PlatformRemotingConstants.DESTROY_CONTROLLER_COMMAND_NAME);
    }
}

