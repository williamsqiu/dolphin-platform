package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformConstants;
import org.opendolphin.core.comm.Command;
import org.opendolphin.core.comm.NamedCommand;

public class DestroyContextCommand extends Command {

    public DestroyContextCommand() {
        super(PlatformConstants.DESTROY_CONTEXT_COMMAND_NAME);
    }
}
