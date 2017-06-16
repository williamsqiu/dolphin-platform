package com.canoo.impl.dp.spring.test;

import com.canoo.dolphin.server.spring.ClientScope;
import com.canoo.impl.platform.core.Assert;
import com.canoo.platform.server.client.ClientSession;

public class TestClientScope extends ClientScope {

    private final ClientSession clientSession;


    public TestClientScope(final ClientSession clientSession) {
        this.clientSession = Assert.requireNonNull(clientSession, "clientSession");
    }

    @Override
    public ClientSession getClientSession() {
        return clientSession;
    }
}
