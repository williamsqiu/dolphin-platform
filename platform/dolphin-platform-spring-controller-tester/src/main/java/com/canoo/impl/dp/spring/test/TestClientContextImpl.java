package com.canoo.impl.dp.spring.test;

import com.canoo.dp.impl.client.ClientContextImpl;
import com.canoo.dp.impl.client.legacy.ClientModelStore;
import com.canoo.dp.impl.client.legacy.communication.AbstractClientConnector;
import com.canoo.dp.impl.remoting.legacy.util.Function;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.session.ClientSessionStore;

import java.net.URL;

public class TestClientContextImpl extends ClientContextImpl implements TestClientContext {

    public TestClientContextImpl(ClientConfiguration clientConfiguration, URL endpoint, Function<ClientModelStore, AbstractClientConnector> connectorProvider, ClientSessionStore clientSessionStore) {
        super(clientConfiguration, endpoint, connectorProvider, clientSessionStore);
    }

    @Override
    public void sendPing() {
        try {
            getDolphinCommandHandler().invokeDolphinCommand(new PingCommand()).get();
        } catch (Exception e) {
            throw new RuntimeException("Error in ping handling", e);
        }
    }
}
