package com.canoo.dp.impl.platform.client.http;

import com.canoo.platform.core.http.ByteArrayProvider;

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
