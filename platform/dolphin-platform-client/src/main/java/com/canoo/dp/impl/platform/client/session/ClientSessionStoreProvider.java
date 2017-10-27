package com.canoo.dp.impl.platform.client.session;

import com.canoo.dp.impl.platform.client.AbstractServiceProvider;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.session.ClientSessionStore;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class ClientSessionStoreProvider extends AbstractServiceProvider<ClientSessionStore> {

    public ClientSessionStoreProvider() {
        super(ClientSessionStore.class);
    }

    @Override
    protected ClientSessionStore createService(ClientConfiguration configuration) {
        return new ClientSessionStoreImpl();
    }
}
