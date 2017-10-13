package com.canoo.dp.impl.client;

import com.canoo.dp.impl.platform.client.AbstractServiceProvider;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.remoting.client.ClientContextFactory;

public class ClientContextFactoryProvider extends AbstractServiceProvider<ClientContextFactory> {

    public ClientContextFactoryProvider() {
        super(ClientContextFactory.class);
    }

    @Override
    protected ClientContextFactory createService(ClientConfiguration configuration) {
        return new ClientContextFactoryImpl();
    }

}
