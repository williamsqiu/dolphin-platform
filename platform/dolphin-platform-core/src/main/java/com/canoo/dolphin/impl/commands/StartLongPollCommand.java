package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformConstants;
import org.opendolphin.core.comm.Command;

public final class StartLongPollCommand extends Command {

    public StartLongPollCommand() {
        super(PlatformConstants.START_LONG_POLL_COMMAND_NAME);
    }
}

