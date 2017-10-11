package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.Services;
import com.canoo.platform.client.http.HttpClient;
import com.canoo.platform.client.http.HttpURLConnectionFactory;
import com.canoo.platform.client.spi.ServiceProvider;
import com.google.gson.Gson;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HttpClientProvider implements ServiceProvider<HttpClient> {

    private final Lock creationLock = new ReentrantLock();

    private HttpClient client;

    @Override
    public HttpClient getService(ClientConfiguration configuration) {
        Assert.requireNonNull(configuration, "configuration");
        creationLock.lock();
        try {
            if (client != null) {
                final HttpURLConnectionFactory connectionFactory = configuration.getObjectProperty("httpURLConnectionFactory", new DefaultHttpURLConnectionFactory());
                client = new HttpClientImpl(Services.getService(Gson.class), connectionFactory);
            }
        } finally {
            creationLock.unlock();
        }
        return client;
    }

    @Override
    public Class<HttpClient> getServiceType() {
        return HttpClient.class;
    }
}
