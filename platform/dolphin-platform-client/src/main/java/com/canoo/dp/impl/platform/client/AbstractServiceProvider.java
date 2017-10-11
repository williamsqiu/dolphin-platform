package com.canoo.dp.impl.platform.client;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.spi.ServiceProvider;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractServiceProvider<S> implements ServiceProvider<S> {

    private final Lock creationLock = new ReentrantLock();

    private S service;

    private final Class<S> serviceClass;

    public AbstractServiceProvider(final Class<S> serviceClass) {
        this.serviceClass = Assert.requireNonNull(serviceClass, "serviceClass");
    }

    protected abstract S createService(final ClientConfiguration configuration);

    @Override
    public final S getService(final ClientConfiguration configuration) {
        Assert.requireNonNull(configuration, "configuration");
        creationLock.lock();
        try {
            this.service = createService(configuration);
        } finally {
            creationLock.unlock();
        }
        return service;
    }

    @Override
    public final Class<S> getServiceType() {
        return serviceClass;
    }
}

