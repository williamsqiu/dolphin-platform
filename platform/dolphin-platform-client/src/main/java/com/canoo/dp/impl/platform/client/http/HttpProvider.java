package com.canoo.dp.impl.platform.client.http;

import org.apiguardian.api.API;

import java.io.IOException;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public interface HttpProvider<R> {

    R get() throws IOException;
}
