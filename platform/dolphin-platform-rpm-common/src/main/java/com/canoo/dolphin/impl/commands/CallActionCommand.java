package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformConstants;
import org.opendolphin.core.comm.Command;

public final class CallActionCommand extends Command {

    public CallActionCommand() {
        super(PlatformConstants.CALL_ACTION_COMMAND_NAME);
    }
}

