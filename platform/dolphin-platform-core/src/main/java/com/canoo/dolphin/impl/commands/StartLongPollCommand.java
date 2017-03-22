package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformConstants;
import org.opendolphin.core.comm.Command;
import org.opendolphin.core.comm.NamedCommand;

public class StartLongPollCommand extends Command {

    public StartLongPollCommand() {
        super(PlatformConstants.START_LONG_POLL_COMMAND_NAME);
    }
}

