package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.http.HttpClient;
import com.canoo.platform.client.http.HttpRequest;
import com.canoo.platform.client.http.HttpURLConnectionFactory;
import com.canoo.platform.client.http.HttpURLConnectionHandler;
import com.canoo.platform.client.http.RequestMethod;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HttpClientImpl implements HttpClient {

    private final Gson gson;

    private HttpURLConnectionFactory httpURLConnectionFactory = new DefaultHttpURLConnectionFactory();

    private final List<HttpURLConnectionHandler> requestHandlers = new CopyOnWriteArrayList<>();

    private final List<HttpURLConnectionHandler> responseHandlers = new CopyOnWriteArrayList<>();

    public HttpClientImpl(Gson gson) {
        this.gson = gson;
    }

    public HttpURLConnectionFactory getConnectionFactory() {
        return httpURLConnectionFactory;
    }

    public void setConnectionFactory(final HttpURLConnectionFactory connectionFactory) {
        this.httpURLConnectionFactory = Assert.requireNonNull(connectionFactory, "connectionFactory");
    }

    @Override
    public void addRequestHandler(final HttpURLConnectionHandler handler) {
        Assert.requireNonNull(handler, "handler");
        requestHandlers.add(handler);
    }

    public void removeRequestHandler(final HttpURLConnectionHandler handler) {
        requestHandlers.remove(handler);
    }

    @Override
    public void addResponseHandler(final HttpURLConnectionHandler handler) {
        Assert.requireNonNull(handler, "handler");
        responseHandlers.add(handler);
    }

    public void removeResponseHandler(final HttpURLConnectionHandler handler) {
        responseHandlers.remove(handler);
    }

    @Override
    public HttpRequest request(final URL url, final RequestMethod method) throws IOException {
        Assert.requireNonNull(url, "url");
        Assert.requireNonNull(method, "method");

        final HttpURLConnection connection = httpURLConnectionFactory.create(url);
        Assert.requireNonNull(connection, "connection");

        connection.setRequestMethod(method.getRawName());
        return new HttpRequestImpl(connection, gson, requestHandlers, responseHandlers);
    }
}
