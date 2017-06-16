package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformRemotingConstants;
import org.opendolphin.core.comm.Command;

public final class CallActionCommand extends Command {

    public CallActionCommand() {
        super(PlatformRemotingConstants.CALL_ACTION_COMMAND_NAME);
    }
}

