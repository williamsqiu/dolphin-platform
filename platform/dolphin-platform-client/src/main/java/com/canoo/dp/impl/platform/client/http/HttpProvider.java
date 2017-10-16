package com.canoo.dp.impl.platform.client.http;

import java.io.IOException;

public interface HttpProvider<R> {

    R get() throws IOException;
}
