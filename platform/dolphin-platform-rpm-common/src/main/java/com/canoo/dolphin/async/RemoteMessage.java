package com.canoo.dolphin.async;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RemoteMessage implements Serializable {

    private List<RemoteCommand> commands = new ArrayList<>();

    public List<RemoteCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<RemoteCommand> commands) {
        this.commands = commands;
    }
}
