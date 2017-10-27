package com.canoo.dp.impl.platform.client.http;

import com.canoo.platform.core.http.ByteArrayProvider;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class SimpleByteArrayProvider implements ByteArrayProvider {

    private final byte[] bytes;

    public SimpleByteArrayProvider(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] get() {
        return bytes;
    }
}
