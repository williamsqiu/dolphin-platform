package com.canoo.impl.dp.spring.test;

import com.canoo.dp.impl.client.ClientContextImpl;
import com.canoo.dp.impl.client.legacy.ClientModelStore;
import com.canoo.dp.impl.client.legacy.communication.AbstractClientConnector;
import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.session.ClientSessionStore;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class TestClientContextImpl extends ClientContextImpl implements TestClientContext {

    public TestClientContextImpl(final ClientConfiguration clientConfiguration, final URI endpoint, final Function<ClientModelStore, AbstractClientConnector> connectorProvider, final ClientSessionStore clientSessionStore) {
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

    @Override
    public void sendPing(long time, TimeUnit unit) {
        Assert.requireNonNull(unit, "unit");
        try {
            getDolphinCommandHandler().invokeDolphinCommand(new PingCommand()).get(time, unit);
        } catch (Exception e) {
            throw new RuntimeException("Error in ping handling", e);
        }
    }
}
