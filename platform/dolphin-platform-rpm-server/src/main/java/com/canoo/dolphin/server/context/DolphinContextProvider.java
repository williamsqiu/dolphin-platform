package com.canoo.dolphin.server.context;

import com.canoo.platform.server.client.ClientSession;

public interface DolphinContextProvider {

    DolphinContext getContext(final ClientSession clientSession);

    DolphinContext getContextById(String clientSessionId);

    DolphinContext getCurrentDolphinContext();
}
