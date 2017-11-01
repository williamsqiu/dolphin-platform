package com.canoo.dp.impl.server.event;

import com.canoo.platform.remoting.server.event.Topic;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.Data;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.util.Iterator;

import static com.canoo.dp.impl.server.event.DistributedEventConstants.CONTEXT_PARAM;
import static com.canoo.dp.impl.server.event.DistributedEventConstants.DATA_PARAM;
import static com.canoo.dp.impl.server.event.DistributedEventConstants.METADATA_KEY_PARAM;
import static com.canoo.dp.impl.server.event.DistributedEventConstants.METADATA_PARAM;
import static com.canoo.dp.impl.server.event.DistributedEventConstants.METADATA_VALUE_PARAM;
import static com.canoo.dp.impl.server.event.DistributedEventConstants.SPEC_1_0;
import static com.canoo.dp.impl.server.event.DistributedEventConstants.SPEC_VERSION_PARAM;
import static com.canoo.dp.impl.server.event.DistributedEventConstants.TIMESTAMP_PARAM;
import static com.canoo.dp.impl.server.event.DistributedEventConstants.TOPIC_PARAM;

public class EventStreamSerializerTests {

    @Test
    public void testSimpleEventToJson() throws IOException, ClassNotFoundException {
        //given
        final Topic<String> topic = Topic.create("test-topic");
        final long timestamp = System.currentTimeMillis();
        final String data = "test-data";
        final DolphinEvent<String> dolphinEvent = new DolphinEvent<>(topic, timestamp, data);

        //when
        final JsonElement root = convert(dolphinEvent);

        //then
        checkJsonSchema(root);
        Assert.assertEquals(Base64Utils.fromBase64(root.getAsJsonObject().getAsJsonPrimitive(DATA_PARAM).getAsString()), data);
        final JsonObject context = root.getAsJsonObject().get(CONTEXT_PARAM).getAsJsonObject();
        Assert.assertEquals(context.getAsJsonPrimitive(TIMESTAMP_PARAM).getAsNumber().longValue(), timestamp);
        Assert.assertEquals(context.getAsJsonPrimitive(TOPIC_PARAM).getAsString(), topic.getName());
        Assert.assertEquals(context.getAsJsonArray(METADATA_PARAM).size(), 0);
    }

    @Test
    public void testEventWithNullDataToJson() throws IOException, ClassNotFoundException {
        //given
        final Topic<String> topic = Topic.create("test-topic");
        final long timestamp = System.currentTimeMillis();
        final String data = null;
        final DolphinEvent<String> dolphinEvent = new DolphinEvent<>(topic, timestamp, data);

        //when
        final JsonElement root = convert(dolphinEvent);

        //then
        checkJsonSchema(root);
        Assert.assertTrue(root.getAsJsonObject().get(DATA_PARAM).isJsonNull());
    }

    @Test
    public void testEventWithSerializedDataToJson() throws IOException, ClassNotFoundException {
        //given
        final Topic<LocalDateTime> topic = Topic.create("test-topic");
        final long timestamp = System.currentTimeMillis();
        final LocalDateTime data = LocalDateTime.now();
        final DolphinEvent<LocalDateTime> dolphinEvent = new DolphinEvent<>(topic, timestamp, data);

        //when
        final JsonElement root = convert(dolphinEvent);

        //then
        checkJsonSchema(root);
        Assert.assertEquals(Base64Utils.fromBase64(root.getAsJsonObject().getAsJsonPrimitive(DATA_PARAM).getAsString()), data);
    }

    @Test
    public void testEventWithMetadataToJson() throws IOException, ClassNotFoundException {
        //given
        final Topic<String> topic = Topic.create("test-topic");
        final long timestamp = System.currentTimeMillis();
        final String data = "test-data";
        final DolphinEvent<String> dolphinEvent = new DolphinEvent<>(topic, timestamp, data);

        final String key1 = "test-key-1";
        final Serializable value1 = "test-value-1";

        final String key2 = "test-key-2";
        final Serializable value2 = null;

        final String key3 = "test-key-3";
        final Serializable value3 = LocalDateTime.now();

        dolphinEvent.addMetadata(key1, value1);
        dolphinEvent.addMetadata(key2, value2);
        dolphinEvent.addMetadata(key3, value3);

        //when
        final JsonElement root = convert(dolphinEvent);

        //then
        checkJsonSchema(root);
        final JsonArray metadata = root.getAsJsonObject().getAsJsonObject(CONTEXT_PARAM).getAsJsonArray(METADATA_PARAM);
        Assert.assertEquals(metadata.size(), 3);
        Assert.assertEquals(getMetadataValueForKey(metadata, key1), value1);
        Assert.assertEquals(getMetadataValueForKey(metadata, key2), value2);
        Assert.assertEquals(getMetadataValueForKey(metadata, key3), value3);
    }

    private Serializable getMetadataValueForKey(final JsonArray metadataArray, final String key) throws IOException, ClassNotFoundException {
        final Iterator<JsonElement> elementIterator = metadataArray.iterator();
        while (elementIterator.hasNext()) {
            final JsonObject metadata = elementIterator.next().getAsJsonObject();
            if(metadata.getAsJsonPrimitive(METADATA_KEY_PARAM).getAsString().equals(key)) {
                if(metadata.get(METADATA_VALUE_PARAM).isJsonNull()) {
                    return null;
                } else {
                    return Base64Utils.fromBase64(metadata.getAsJsonPrimitive(METADATA_VALUE_PARAM).getAsString());
                }
            }
        }
        Assert.fail("metadata do not contain key '" + key + "'");
        throw new IllegalStateException("metadata do not contain key '" + key + "'");
    }

    private <T extends Serializable> JsonElement convert(DolphinEvent<T> event) throws IOException {
        final EventStreamSerializer streamSerializer = new EventStreamSerializer();
        final ObjectDataOutput output = new ByteObjectDataOutput();
        streamSerializer.write(output, event);
        final byte[] rawOutputData = output.toByteArray();
        final String outputData = new String(rawOutputData);
        return new JsonParser().parse(outputData);
    }

    private void checkJsonSchema(final JsonElement root) {
        Assert.assertNotNull(root);
        Assert.assertTrue(root.isJsonObject());

        Assert.assertTrue(root.getAsJsonObject().has(SPEC_VERSION_PARAM));
        Assert.assertTrue(root.getAsJsonObject().get(SPEC_VERSION_PARAM).isJsonPrimitive());
        Assert.assertTrue(root.getAsJsonObject().getAsJsonPrimitive(SPEC_VERSION_PARAM).isString());
        Assert.assertEquals(root.getAsJsonObject().getAsJsonPrimitive(SPEC_VERSION_PARAM).getAsString(), SPEC_1_0);

        Assert.assertTrue(root.getAsJsonObject().has(DATA_PARAM));
        Assert.assertTrue(root.getAsJsonObject().get(DATA_PARAM).isJsonPrimitive() || root.getAsJsonObject().get(DATA_PARAM).isJsonNull());
        if(root.getAsJsonObject().get(DATA_PARAM).isJsonPrimitive()) {
            Assert.assertTrue(root.getAsJsonObject().getAsJsonPrimitive(DATA_PARAM).isString());
        }

        Assert.assertTrue(root.getAsJsonObject().has(CONTEXT_PARAM));
        Assert.assertTrue(root.getAsJsonObject().get(CONTEXT_PARAM).isJsonObject());

        final JsonObject context = root.getAsJsonObject().getAsJsonObject(CONTEXT_PARAM);

        Assert.assertTrue(context.has(TIMESTAMP_PARAM));
        Assert.assertTrue(context.get(TIMESTAMP_PARAM).isJsonPrimitive());
        Assert.assertTrue(context.getAsJsonPrimitive(TIMESTAMP_PARAM).isNumber());

        Assert.assertTrue(context.has(TOPIC_PARAM));
        Assert.assertTrue(context.get(TOPIC_PARAM).isJsonPrimitive());
        Assert.assertTrue(context.getAsJsonPrimitive(TOPIC_PARAM).isString());

        Assert.assertTrue(context.has(METADATA_PARAM));
        Assert.assertTrue(context.get(METADATA_PARAM).isJsonArray());
        final JsonArray metadataArray = context.getAsJsonArray(METADATA_PARAM);
        if(metadataArray.size() > 0) {
            final Iterator<JsonElement> elementIterator = metadataArray.iterator();
            while (elementIterator.hasNext()) {
                JsonElement metadataElem = elementIterator.next();
                Assert.assertTrue(metadataElem.isJsonObject());
                final JsonObject metadata = metadataElem.getAsJsonObject();
                Assert.assertTrue(metadata.has(METADATA_KEY_PARAM));
                Assert.assertTrue(metadata.get(METADATA_KEY_PARAM).isJsonPrimitive());
                Assert.assertTrue(metadata.getAsJsonPrimitive(METADATA_KEY_PARAM).isString());

                Assert.assertTrue(metadata.has(METADATA_VALUE_PARAM));
                Assert.assertTrue(metadata.get(METADATA_VALUE_PARAM).isJsonPrimitive() ||
                        metadata.get(METADATA_VALUE_PARAM).isJsonNull());
                if(metadata.get(METADATA_VALUE_PARAM).isJsonPrimitive()) {
                    Assert.assertTrue(metadata.getAsJsonPrimitive(METADATA_VALUE_PARAM).isString());
                }
            }
        }
    }

    private class ByteObjectDataOutput implements ObjectDataOutput {

        private final StringBuffer content = new StringBuffer();

        @Override
        public void writeByteArray(byte[] bytes) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeBooleanArray(boolean[] booleans) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeCharArray(char[] chars) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeIntArray(int[] ints) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeLongArray(long[] longs) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeDoubleArray(double[] values) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeFloatArray(float[] values) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeShortArray(short[] values) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeUTFArray(String[] values) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeObject(Object object) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeData(Data data) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public byte[] toByteArray() {
            return content.toString().getBytes();
        }

        @Override
        public ByteOrder getByteOrder() {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void write(int b) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void write(byte[] b) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeBoolean(boolean v) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeByte(int v) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeShort(int v) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeChar(int v) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeInt(int v) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeLong(long v) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeFloat(float v) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeDouble(double v) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeBytes(String s) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeChars(String s) throws IOException {
            throw new RuntimeException("Not needed for test");
        }

        @Override
        public void writeUTF(String s) throws IOException {
            content.append(s);
        }
    }

}
