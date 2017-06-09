package com.canoo.dolphin.test.impl;

import com.canoo.dolphin.server.spring.ClientScope;
import com.canoo.dolphin.util.Assert;
import com.canoo.platform.server.client.ClientSession;

/**
 * Created by hendrikebbers on 09.06.17.
 */
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
