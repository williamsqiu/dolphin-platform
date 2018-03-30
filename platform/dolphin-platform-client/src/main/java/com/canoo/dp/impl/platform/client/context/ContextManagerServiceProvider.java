package com.canoo.dp.impl.platform.client.context;

import com.canoo.dp.impl.platform.client.AbstractServiceProvider;
import com.canoo.dp.impl.platform.core.context.ContextManagerImpl;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.core.context.ContextManager;

public class ContextManagerServiceProvider extends AbstractServiceProvider<ContextManager> {

    public ContextManagerServiceProvider() {
        super(ContextManager.class);
    }

    @Override
    protected ContextManager createService(final ClientConfiguration configuration) {
        return ContextManagerImpl.getInstance();
    }
}
