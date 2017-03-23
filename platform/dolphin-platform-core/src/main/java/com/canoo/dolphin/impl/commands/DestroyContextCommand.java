package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformConstants;
import org.opendolphin.core.comm.Command;

public final class DestroyContextCommand extends Command {

    public DestroyContextCommand() {
        super(PlatformConstants.DESTROY_CONTEXT_COMMAND_NAME);
    }
}
