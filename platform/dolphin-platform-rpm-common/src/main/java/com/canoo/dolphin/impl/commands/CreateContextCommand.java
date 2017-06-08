package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformRemotingConstants;
import org.opendolphin.core.comm.Command;

public final class CreateContextCommand extends Command {

    public CreateContextCommand() {
        super(PlatformRemotingConstants.CREATE_CONTEXT_COMMAND_NAME);
    }
}
