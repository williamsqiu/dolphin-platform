package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformConstants;
import org.opendolphin.core.comm.Command;

public class CreateControllerCommand extends Command {

    public CreateControllerCommand() {
        super(PlatformConstants.CREATE_CONTROLLER_COMMAND_NAME);
    }
}
