package com.canoo.dp.impl.remoting.codec.encoders;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.remoting.legacy.communication.PresentationModelDeletedCommand;
import com.google.gson.JsonObject;
import org.apiguardian.api.API;

import static com.canoo.dp.impl.remoting.legacy.communication.CommandConstants.ID;
import static com.canoo.dp.impl.remoting.legacy.communication.CommandConstants.PM_ID;
import static com.canoo.dp.impl.remoting.legacy.communication.CommandConstants.PRESENTATION_MODEL_DELETED_COMMAND_ID;
import static org.apiguardian.api.API.Status.DEPRECATED;

@Deprecated
@API(since = "0.x", status = DEPRECATED)
public class PresentationModelDeletedCommandEncoder extends AbstractCommandTranscoder<PresentationModelDeletedCommand> {

    @Override
    public JsonObject encode(PresentationModelDeletedCommand command) {
        Assert.requireNonNull(command, "command");
        final JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(ID, PRESENTATION_MODEL_DELETED_COMMAND_ID);
        jsonCommand.addProperty(PM_ID, command.getPmId());
        return jsonCommand;
    }

    @Override
    public PresentationModelDeletedCommand decode(JsonObject jsonObject) {
        PresentationModelDeletedCommand command = new PresentationModelDeletedCommand();
        command.setPmId(getStringElement(jsonObject, PM_ID));
        return command;
    }
}
