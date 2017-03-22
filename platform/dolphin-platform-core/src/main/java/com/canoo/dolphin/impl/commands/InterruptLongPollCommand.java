package com.canoo.dolphin.impl.commands;

import com.canoo.dolphin.impl.PlatformConstants;
import org.opendolphin.core.comm.SignalCommand;

public final class InterruptLongPollCommand extends SignalCommand {

    public InterruptLongPollCommand() {
        super(PlatformConstants.INTERRUPT_LONG_POLL_COMMAND_NAME);
    }
}


