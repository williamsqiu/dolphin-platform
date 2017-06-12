package com.canoo.impl.server.context;

import com.canoo.dolphin.BeanManager;
import com.canoo.platform.server.ClientSessionExecutor;
import com.canoo.platform.server.RemotingContext;
import com.canoo.platform.server.binding.PropertyBinder;
import com.canoo.impl.server.binding.PropertyBinderImpl;
import com.canoo.platform.server.event.DolphinEventBus;
import com.canoo.dolphin.util.Assert;

import java.util.concurrent.Executor;

public class RemotingContextImpl implements RemotingContext {

    private final DolphinContext dolphinContext;

    private final DolphinEventBus eventBus;

    private final PropertyBinder propertyBinder = new PropertyBinderImpl();

    private final ClientSessionExecutor clientSessionExecutor;

    public RemotingContextImpl(final DolphinContext dolphinContext, DolphinEventBus eventBus) {
        this.dolphinContext = Assert.requireNonNull(dolphinContext, "dolphinContext");
        this.eventBus = Assert.requireNonNull(eventBus, "eventBus");
        clientSessionExecutor = new ClientSessionExecutorImpl(new Executor() {
            @Override
            public void execute(Runnable command) {
                dolphinContext.runLater(command);
            }
        });
    }

    @Override
    public String getId() {
        return dolphinContext.getId();
    }

    @Override
    public ClientSessionExecutor createSessionExecutor() {
        return clientSessionExecutor;
    }

    @Override
    public PropertyBinder getBinder() {
        return propertyBinder;
    }

    @Override
    public BeanManager getBeanManager() {
        return dolphinContext.getBeanManager();
    }

    @Override
    public DolphinEventBus getEventBus() {
        return eventBus;
    }
}
