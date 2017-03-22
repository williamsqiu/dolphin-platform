package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformConstants;
import org.opendolphin.core.comm.Command;

public class CreateContextCommand extends Command {

    public CreateContextCommand() {
        super(PlatformConstants.CREATE_CONTEXT_COMMAND_NAME);
    }
}
