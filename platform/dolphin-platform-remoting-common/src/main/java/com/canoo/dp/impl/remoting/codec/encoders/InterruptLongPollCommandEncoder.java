package com.canoo.dp.impl.remoting.codec.encoders;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.remoting.legacy.commands.InterruptLongPollCommand;
import com.google.gson.JsonObject;
import org.apiguardian.api.API;

import static com.canoo.dp.impl.remoting.legacy.communication.CommandConstants.ID;
import static com.canoo.dp.impl.remoting.legacy.communication.CommandConstants.INTERRUPT_LONG_POLL_COMMAND_ID;
import static org.apiguardian.api.API.Status.DEPRECATED;

@Deprecated
@API(since = "0.x", status = DEPRECATED)
public class InterruptLongPollCommandEncoder extends AbstractCommandTranscoder<InterruptLongPollCommand> {

    @Override
    public JsonObject encode(InterruptLongPollCommand command) {
        Assert.requireNonNull(command, "command");
        final JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(ID, INTERRUPT_LONG_POLL_COMMAND_ID);
        return jsonCommand;
    }

    @Override
    public InterruptLongPollCommand decode(JsonObject jsonObject) {
        return new InterruptLongPollCommand();
    }
}
