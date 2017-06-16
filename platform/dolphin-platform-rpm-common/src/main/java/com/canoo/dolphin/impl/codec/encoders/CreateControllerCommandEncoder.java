package com.canoo.dolphin.impl.codec.encoders;

import com.canoo.dolphin.impl.commands.CreateControllerCommand;
import com.canoo.impl.platform.core.Assert;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import static com.canoo.dolphin.impl.codec.CommandConstants.*;

public class CreateControllerCommandEncoder implements CommandEncoder<CreateControllerCommand> {

    @Override
    public JsonObject encode(final CreateControllerCommand command) {
        Assert.requireNonNull(command, "command");
        final JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(PARENT_CONTROLLER_ID, command.getParentControllerId());
        jsonCommand.addProperty(CONTROLLER_NAME, command.getControllerName());
        jsonCommand.addProperty(ID, CREATE_CONTROLLER_COMMAND_ID);
        return jsonCommand;
    }

    @Override
    public CreateControllerCommand decode(JsonObject jsonObject) {
        Assert.requireNonNull(jsonObject, "jsonObject");
        try {
            final CreateControllerCommand command = new CreateControllerCommand();
            final JsonPrimitive parentControllerPrimitive = jsonObject.getAsJsonPrimitive(PARENT_CONTROLLER_ID);
            if(parentControllerPrimitive != null) {
                command.setParentControllerId(parentControllerPrimitive.getAsString());
            }
            command.setControllerName(jsonObject.getAsJsonPrimitive(CONTROLLER_NAME).getAsString());
            return command;
        } catch (Exception ex) {
            throw new JsonParseException("Illegal JSON detected", ex);
        }
    }
}
