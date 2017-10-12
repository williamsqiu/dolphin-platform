package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.client.AbstractServiceProvider;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.client.http.HttpClient;
import com.canoo.platform.client.http.HttpURLConnectionFactory;
import com.canoo.platform.client.http.spi.RequestHandlerProvider;
import com.canoo.platform.client.http.spi.ResponseHandlerProvider;
import com.google.gson.Gson;

import java.util.Iterator;
import java.util.ServiceLoader;

public class HttpClientProvider extends AbstractServiceProvider<HttpClient> {

    public HttpClientProvider() {
        super(HttpClient.class);
    }

    @Override
    protected HttpClient createService(ClientConfiguration configuration) {
        final HttpURLConnectionFactory connectionFactory = configuration.getObjectProperty("httpURLConnectionFactory", new DefaultHttpURLConnectionFactory());
        final HttpClientImpl client = new HttpClientImpl(PlatformClient.getService(Gson.class), connectionFactory, configuration);

        final ServiceLoader<RequestHandlerProvider> requestLoader = ServiceLoader.load(RequestHandlerProvider.class);
        final Iterator<RequestHandlerProvider> requestIterator = requestLoader.iterator();
        while (requestIterator.hasNext()) {
            client.addRequestHandler(requestIterator.next().getHandler(configuration));
        }

        final ServiceLoader<ResponseHandlerProvider> responseLoader = ServiceLoader.load(ResponseHandlerProvider.class);
        final Iterator<ResponseHandlerProvider> responseIterator = responseLoader.iterator();
        while (responseIterator.hasNext()) {
            client.addResponseHandler(responseIterator.next().getHandler(configuration));
        }
        return client;
    }
}
