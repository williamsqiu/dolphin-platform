package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.http.DefaultHttpURLConnectionFactory;
import com.canoo.dp.impl.platform.core.http.HttpClientConnection;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.core.http.HttpClient;
import com.canoo.platform.core.http.HttpCallRequestBuilder;
import com.canoo.platform.core.http.HttpURLConnectionFactory;
import com.canoo.platform.core.http.HttpURLConnectionHandler;
import com.canoo.platform.core.http.RequestMethod;
import com.canoo.platform.core.DolphinRuntimeException;
import com.google.gson.Gson;
import org.apiguardian.api.API;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class HttpClientImpl implements HttpClient {

    private final Gson gson;

    private final HttpURLConnectionFactory httpURLConnectionFactory;

    private final List<HttpURLConnectionHandler> requestHandlers = new CopyOnWriteArrayList<>();

    private final List<HttpURLConnectionHandler> responseHandlers = new CopyOnWriteArrayList<>();

    private final ClientConfiguration configuration;

    public HttpClientImpl(final Gson gson, final ClientConfiguration configuration) {
        this(gson, new DefaultHttpURLConnectionFactory(), configuration);
    }

    public HttpClientImpl(final Gson gson, final HttpURLConnectionFactory httpURLConnectionFactory, final ClientConfiguration configuration) {
        this.gson = Assert.requireNonNull(gson, "gson");
        this.httpURLConnectionFactory = Assert.requireNonNull(httpURLConnectionFactory, "httpURLConnectionFactory");
        this.configuration = configuration;
    }

    public void addRequestHandler(final HttpURLConnectionHandler handler) {
        Assert.requireNonNull(handler, "handler");
        requestHandlers.add(handler);
    }

    @Override
    public void addResponseHandler(final HttpURLConnectionHandler handler) {
        Assert.requireNonNull(handler, "handler");
        responseHandlers.add(handler);
    }

    @Override
    public HttpCallRequestBuilder request(final String url, final RequestMethod method) {
        try {
            return request(new URI(url), method);
        } catch (final URISyntaxException e) {
            throw new DolphinRuntimeException("HTTP error", e);
        }
    }

    @Override
    public HttpCallRequestBuilder request(final URI url, final RequestMethod method) {
        try {
            Assert.requireNonNull(url, "url");
            Assert.requireNonNull(method, "method");
            final HttpClientConnection clientConnection = new HttpClientConnection(httpURLConnectionFactory, url, method);
            return new HttpCallRequestBuilderImpl(clientConnection, gson, requestHandlers, responseHandlers, configuration);
        } catch (final IOException e) {
            throw new DolphinRuntimeException("HTTP error", e);
        }
    }
}
