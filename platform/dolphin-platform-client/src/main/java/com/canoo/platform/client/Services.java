package com.canoo.platform.client;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.spi.ServiceProvider;
import com.canoo.platform.core.DolphinRuntimeException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

public class Services {

    private final static Services INSTANCE = new Services();

    private final Map<Class, Object> services = new HashMap<>();

    private Services() {
        final ServiceLoader<ServiceProvider> loader = ServiceLoader.load(ServiceProvider.class);
        final Iterator<ServiceProvider> iterator = loader.iterator();
        while (iterator.hasNext()) {
            ServiceProvider provider = iterator.next();
            Class serviceClass = provider.getServiceType();
            Assert.requireNonNull(serviceClass, "serviceClass");
            if (services.containsKey(serviceClass)) {
                throw new DolphinRuntimeException("Can not register more than 1 implementation for service type " + serviceClass);
            }
            Object service = provider.getService(null);
            Assert.requireNonNull(service, "service");
            services.put(serviceClass, service);
        }
    }

    private <S> boolean hasServiceImpl(final Class<S> serviceClass) {
        Assert.requireNonNull(serviceClass, "serviceClass");
        return services.containsKey(serviceClass);
    }

    private <S> S getServiceImpl(final Class<S> serviceClass) {
        Assert.requireNonNull(serviceClass, "serviceClass");
        S service = (S) services.get(serviceClass);
        Assert.requireNonNull(service, "service");
        return service;
    }

    public static <S> boolean hasService(final Class<S> serviceClass) {
        return getInstance().hasServiceImpl(serviceClass);
    }

    public static <S> S getService(final Class<S> serviceClass) {
        return getInstance().getServiceImpl(serviceClass);
    }

    private static Services getInstance() {
        return INSTANCE;
    }
}
