package com.canoo.dolphin.impl.codec.encoders;

import com.canoo.dolphin.impl.commands.CallActionCommand;
import com.canoo.impl.platform.core.Assert;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.Map;

import static com.canoo.dolphin.impl.codec.CommandConstants.*;

public class CallActionCommandEncoder implements CommandEncoder<CallActionCommand> {

    @Override
    public JsonObject encode(final CallActionCommand command) {
        Assert.requireNonNull(command, "command");
        final JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(CONTROLLER_ID, command.getControllerId());
        jsonCommand.addProperty(ACTION_NAME, command.getActionName());

        final JsonArray paramArray = new JsonArray();
        for(Map.Entry<String, Object> paramEntry : command.getParams().entrySet()) {
            final JsonObject paramObject = new JsonObject();
            jsonCommand.addProperty(PARAM_NAME, paramEntry.getKey());
            jsonCommand.add(PARAM_VALUE, ValueEncoder.encodeValue(paramEntry.getValue()));
            paramArray.add(paramObject);
        }
        jsonCommand.add(PARAMS, paramArray);

        jsonCommand.addProperty(ID, CALL_ACTION_COMMAND_ID);
        return jsonCommand;
    }

    @Override
    public CallActionCommand decode(JsonObject jsonObject) {
        Assert.requireNonNull(jsonObject, "jsonObject");
        try {
            final CallActionCommand command = new CallActionCommand();
            command.setControllerId(jsonObject.getAsJsonPrimitive(CONTROLLER_ID).getAsString());
            command.setActionName(jsonObject.getAsJsonPrimitive(ACTION_NAME).getAsString());

            final JsonArray jsonArray = jsonObject.getAsJsonArray(PARAMS);
            if(jsonArray != null) {
                for (final JsonElement jsonElement : jsonArray) {
                    final JsonObject paramObject = jsonElement.getAsJsonObject();
                    command.addParam(paramObject.getAsJsonPrimitive(PARAM_NAME).getAsString(), ValueEncoder.decodeValue(paramObject.get(PARAM_VALUE)));
                }
            }
            return command;
        } catch (Exception ex) {
            throw new JsonParseException("Illegal JSON detected", ex);
        }
    }
}
