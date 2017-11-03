package com.canoo.dp.impl.server.event;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

/**
 * Created by hendrikebbers on 01.11.17.
 */
public class Base64Utils {

    public static String toBase64(final Serializable data) throws IOException {
        final ByteArrayOutputStream rawOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream dataOutputStream = new ObjectOutputStream(rawOutputStream);
        dataOutputStream.writeObject(data);
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(rawOutputStream.toByteArray());
    }

    public static Serializable fromBase64(final String data) throws IOException, ClassNotFoundException {
        final Base64.Decoder decoder = Base64.getDecoder();
        final byte[] raw = decoder.decode(data);
        final ByteArrayInputStream rawInputStream = new ByteArrayInputStream(raw);
        final ObjectInputStream dataInputStream = new ObjectInputStream(rawInputStream);
        return (Serializable) dataInputStream.readObject();
    }

}
