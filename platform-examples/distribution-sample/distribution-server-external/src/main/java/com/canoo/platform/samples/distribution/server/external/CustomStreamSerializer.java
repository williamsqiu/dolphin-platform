package com.canoo.platform.samples.distribution.server.external;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class CustomStreamSerializer implements StreamSerializer<CustomEventFormat> {

    @Override
    public void write(ObjectDataOutput out, CustomEventFormat object) throws IOException {
        final JsonObject root = new JsonObject();
        root.addProperty("shared-event-spec-version", "1.0");
        root.addProperty("data", toBase64(object.getMyMessage()));
        final JsonObject context = new JsonObject();
        context.addProperty("timestamp", System.currentTimeMillis());
        context.addProperty("topic", object.getTopic());
        context.add("metadata", new JsonArray());
        root.add("context", context);
        final String json = new GsonBuilder().serializeNulls().create().toJson(root);
        out.writeUTF(json);
    }

    @Override
    public CustomEventFormat read(ObjectDataInput in) throws IOException {
        final JsonElement element = new JsonParser().parse(in.readUTF());
        try {
            return new CustomEventFormat(fromBase64(element.getAsJsonObject().get("data").getAsString()), element.getAsJsonObject().get("context").getAsJsonObject().get("topic").getAsString());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error", e);
        }
    }

    @Override
    public int getTypeId() {
        return 4711;
    }

    @Override
    public void destroy() {
    }

    private String toBase64(final String data) throws IOException {
        final ByteArrayOutputStream rawOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream dataOutputStream = new ObjectOutputStream(rawOutputStream);
        dataOutputStream.writeObject(data);
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(rawOutputStream.toByteArray());
    }

    private String fromBase64(final String data) throws IOException, ClassNotFoundException {
        final Base64.Decoder decoder = Base64.getDecoder();
        final byte[] raw = decoder.decode(data);
        final ByteArrayInputStream rawInputStream = new ByteArrayInputStream(raw);
        final ObjectInputStream dataInputStream = new ObjectInputStream(rawInputStream);
        return (String) dataInputStream.readObject();
    }
}
