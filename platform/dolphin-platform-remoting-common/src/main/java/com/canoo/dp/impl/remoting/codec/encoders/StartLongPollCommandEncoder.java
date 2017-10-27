package com.canoo.dp.impl.remoting.codec.encoders;

import com.canoo.dp.impl.remoting.legacy.commands.StartLongPollCommand;
import com.canoo.dp.impl.platform.core.Assert;
import com.google.gson.JsonObject;
import org.apiguardian.api.API;

import static com.canoo.dp.impl.remoting.legacy.communication.CommandConstants.ID;
import static com.canoo.dp.impl.remoting.legacy.communication.CommandConstants.START_LONG_POLL_COMMAND_ID;
import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class StartLongPollCommandEncoder extends AbstractCommandTranscoder<StartLongPollCommand> {

    @Override
    public JsonObject encode(StartLongPollCommand command) {
        Assert.requireNonNull(command, "command");
        final JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(ID, START_LONG_POLL_COMMAND_ID);
        return jsonCommand;
    }

    @Override
    public StartLongPollCommand decode(JsonObject jsonObject) {
        return new StartLongPollCommand();
    }
}
