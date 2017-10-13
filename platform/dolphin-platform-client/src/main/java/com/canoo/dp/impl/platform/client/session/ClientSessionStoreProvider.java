package com.canoo.dp.impl.platform.client.session;

import com.canoo.dp.impl.platform.client.AbstractServiceProvider;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.session.ClientSessionStore;

public class ClientSessionStoreProvider extends AbstractServiceProvider<ClientSessionStore> {

    public ClientSessionStoreProvider() {
        super(ClientSessionStore.class);
    }

    @Override
    protected ClientSessionStore createService(ClientConfiguration configuration) {
        return new ClientSessionStoreImpl();
    }
}
