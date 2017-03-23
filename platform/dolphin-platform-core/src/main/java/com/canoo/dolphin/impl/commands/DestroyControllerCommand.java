package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformConstants;
import org.opendolphin.core.comm.Command;

public final class DestroyControllerCommand extends Command {

    public DestroyControllerCommand() {
        super(PlatformConstants.DESTROY_CONTROLLER_COMMAND_NAME);
    }
}

