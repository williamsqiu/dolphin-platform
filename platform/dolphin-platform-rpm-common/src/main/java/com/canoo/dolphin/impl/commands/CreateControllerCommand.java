package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformRemotingConstants;
import org.opendolphin.core.comm.Command;

public final class CreateControllerCommand extends Command {

    public CreateControllerCommand() {
        super(PlatformRemotingConstants.CREATE_CONTROLLER_COMMAND_NAME);
    }
}
