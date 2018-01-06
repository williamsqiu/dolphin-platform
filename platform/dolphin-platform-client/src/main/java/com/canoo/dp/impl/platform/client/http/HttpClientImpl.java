package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.http.DefaultHttpURLConnectionFactory;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.core.http.HttpClient;
import com.canoo.platform.core.http.HttpRequest;
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

    public HttpClientImpl(final Gson gson, ClientConfiguration configuration) {
        this(gson, new DefaultHttpURLConnectionFactory(), configuration);
    }

    public HttpClientImpl(Gson gson, HttpURLConnectionFactory httpURLConnectionFactory, ClientConfiguration configuration) {
        this.gson = Assert.requireNonNull(gson, "gson");
        this.httpURLConnectionFactory = Assert.requireNonNull(httpURLConnectionFactory, "httpURLConnectionFactory");
        this.configuration = configuration;
    }

    public HttpURLConnectionFactory getConnectionFactory() {
        return httpURLConnectionFactory;
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
    public HttpRequest request(final URI url, final RequestMethod method) {
        try {
            Assert.requireNonNull(url, "url");
            Assert.requireNonNull(method, "method");

            final HttpURLConnection connection = httpURLConnectionFactory.create(url);
            Assert.requireNonNull(connection, "connection");

            connection.setRequestMethod(method.getRawName());
            return new HttpRequestImpl(connection, gson, requestHandlers, responseHandlers, configuration);
        } catch (IOException e) {
            throw new DolphinRuntimeException("HTTP error", e);
        }
    }

    @Override
    public HttpRequest request(String url, RequestMethod method) {
        try {
            return request(new URI(url), method);
        } catch (URISyntaxException e) {
            throw new DolphinRuntimeException("HTTP error", e);
        }
    }

    @Override
    public HttpRequest request(URI url) {
        return request(url, RequestMethod.GET);
    }

    @Override
    public HttpRequest request(String url) {
        return request(url, RequestMethod.GET);
    }
}
