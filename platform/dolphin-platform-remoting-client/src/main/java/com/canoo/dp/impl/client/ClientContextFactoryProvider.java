package com.canoo.dp.impl.client;

import com.canoo.dp.impl.platform.client.AbstractServiceProvider;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.remoting.client.ClientContextFactory;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class ClientContextFactoryProvider extends AbstractServiceProvider<ClientContextFactory> {

    public ClientContextFactoryProvider() {
        super(ClientContextFactory.class);
    }

    @Override
    protected ClientContextFactory createService(ClientConfiguration configuration) {
        return new ClientContextFactoryImpl();
    }

}
