package com.canoo.dp.impl.platform.client.json;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.spi.ServiceProvider;
import com.google.gson.Gson;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JsonProvider implements ServiceProvider<Gson> {

    private final Lock creationLock = new ReentrantLock();

    private Gson gson;

    @Override
    public Gson getService(ClientConfiguration configuration) {
        Assert.requireNonNull(configuration, "configuration");
        creationLock.lock();
        try {
            if (gson != null) {
                gson = new Gson();
            }
        } finally {
            creationLock.unlock();
        }
        return gson;
    }

    @Override
    public Class<Gson> getServiceType() {
        return Gson.class;
    }
}
