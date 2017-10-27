package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.PlatformConstants;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.core.http.ByteArrayProvider;
import com.canoo.platform.core.http.HttpRequest;
import com.canoo.platform.core.http.HttpResponse;
import com.canoo.platform.core.http.HttpURLConnectionHandler;
import com.canoo.platform.core.DolphinRuntimeException;
import com.google.gson.Gson;
import org.apiguardian.api.API;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class HttpRequestImpl implements HttpRequest {

    private final HttpURLConnection connection;

    private final Gson gson;

    private final AtomicBoolean sent = new AtomicBoolean(false);

    private final List<HttpURLConnectionHandler> requestHandlers;

    private final List<HttpURLConnectionHandler> responseHandlers;

    private final ClientConfiguration configuration;

    private ByteArrayProvider dataProvider = new ByteArrayProvider() {
        @Override
        public byte[] get() {
            return new byte[0];
        }
    };

    public HttpRequestImpl(final HttpURLConnection connection, final Gson gson, final List<HttpURLConnectionHandler> requestHandlers, final List<HttpURLConnectionHandler> responseHandlers, ClientConfiguration configuration) {
        this.connection = Assert.requireNonNull(connection, "connection");
        this.gson = Assert.requireNonNull(gson, "gson");
        this.configuration = configuration;


        Assert.requireNonNull(requestHandlers, "requestHandlers");
        this.requestHandlers = Collections.unmodifiableList(requestHandlers);

        Assert.requireNonNull(responseHandlers, "responseHandlers");
        this.responseHandlers = Collections.unmodifiableList(responseHandlers);
    }

    @Override
    public HttpResponse withContent(final byte[] content) {
        return withContent(content, "application/raw");
    }

    @Override
    public HttpResponse withContent(final byte[] content, final String contentType) {
        connection.setRequestProperty( "Content-Type", contentType);
        connection.setRequestProperty( "Content-Length", content.length + "");
        connection.setUseCaches( false );

        connection.setDoOutput(true);
        dataProvider = new ByteArrayProvider() {
            @Override
            public byte[] get() {
                return content;
            }
        };
        return send();
    }

    @Override
    public HttpResponse withContent(final String content) {
        return withContent(content, "application/txt;charset=utf-8");
    }

    @Override
    public HttpResponse withContent(final String content, final String contentType) {
        connection.setRequestProperty( "charset", "UTF-8");
        try {
            return withContent(content.getBytes(PlatformConstants.CHARSET), contentType);
        } catch (UnsupportedEncodingException e) {
           throw new DolphinRuntimeException("Encoding error", e);
        }
    }

    @Override
    public <I> HttpResponse withContent(final I content) {
        return withContent(gson.toJson(content), "application/json;charset=utf-8");
    }

    @Override
    public HttpResponse withoutContent() {
        return send();
    }

    private HttpResponse send() {
        if(sent.get()) {
            throw new DolphinRuntimeException("Request already sent");
        }
        sent.set(true);
        for(HttpURLConnectionHandler handler : requestHandlers) {
            handler.handle(connection);
        }
        return new HttpResponseImpl(connection, gson, dataProvider, responseHandlers, configuration);
    }

}
