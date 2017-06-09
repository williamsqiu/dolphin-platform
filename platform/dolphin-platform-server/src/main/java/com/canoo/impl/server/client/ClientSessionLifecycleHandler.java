package com.canoo.impl.server.client;

import com.canoo.dolphin.Subscription;
import com.canoo.dolphin.util.Callback;
import com.canoo.platform.server.client.ClientSession;

public interface ClientSessionLifecycleHandler {

    Subscription addSessionCreatedListener(final Callback<ClientSession> listener);

    Subscription addSessionDestroyedListener(final Callback<ClientSession> listener);

    void onSessionCreated(final ClientSession session);

    void onSessionDestroyed(final ClientSession session);

}
