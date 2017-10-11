package com.canoo.dp.impl.platform.client.http;

import java.io.IOException;

/**
 * Created by hendrikebbers on 11.10.17.
 */
public interface HttpProvider<R> {

    R get() throws IOException;
}
