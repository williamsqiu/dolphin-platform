package com.canoo.dolphin.client.async;

import java.io.IOException;
import java.net.URI;

public interface AsyncClient {

    AsyncConnection connect(URI endpoint, AsyncHandler handler) throws IOException;

}
