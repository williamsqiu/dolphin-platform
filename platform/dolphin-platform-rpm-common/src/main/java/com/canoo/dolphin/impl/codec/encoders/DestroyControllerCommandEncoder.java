package com.canoo.dolphin.impl.codec.encoders;

import com.canoo.dolphin.impl.commands.DestroyControllerCommand;
import com.canoo.impl.platform.core.Assert;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import static com.canoo.dolphin.impl.codec.CommandConstants.*;

public class DestroyControllerCommandEncoder implements CommandEncoder<DestroyControllerCommand> {

    @Override
    public JsonObject encode(final DestroyControllerCommand command) {
        Assert.requireNonNull(command, "command");
        final JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(CONTROLLER_ID, command.getControllerId());
        jsonCommand.addProperty(ID, DESTROY_CONTROLLER_COMMAND_ID);
        return jsonCommand;
    }

    @Override
    public DestroyControllerCommand decode(final JsonObject jsonObject) {
        Assert.requireNonNull(jsonObject, "jsonObject");
        try {
            final DestroyControllerCommand command = new DestroyControllerCommand();
            command.setControllerId(jsonObject.getAsJsonPrimitive(CONTROLLER_ID).getAsString());
            return command;
        } catch (Exception ex) {
            throw new JsonParseException("Illegal JSON detected", ex);
        }
    }
}
