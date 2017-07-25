package com.canoo.dp.impl.remoting.codec.encoders;

import com.canoo.dp.impl.remoting.commands.DestroyContextCommand;
import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.remoting.codec.CommandConstants;
import com.google.gson.JsonObject;

public class DestroyContextCommandEncoder extends AbstractCommandEncoder<DestroyContextCommand> {
    @Override
    public JsonObject encode(DestroyContextCommand command) {
        Assert.requireNonNull(command, "command");
        final JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(CommandConstants.ID, CommandConstants.DESTROY_CONTEXT_COMMAND_ID);
        return jsonCommand;
    }

    @Override
    public DestroyContextCommand decode(JsonObject jsonObject) {
        return new DestroyContextCommand();
    }
}
