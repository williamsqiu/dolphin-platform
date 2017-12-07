package com.canoo.impl.dp.spring.test;

import com.canoo.dp.impl.remoting.legacy.communication.Command;

public final class PingCommand extends Command {

    public PingCommand() {
        super("PING");
    }
}
