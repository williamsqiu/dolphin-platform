package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.PlatformConstants;
import com.canoo.platform.client.http.HttpRequest;
import com.canoo.platform.client.http.HttpResponse;
import com.canoo.platform.client.http.HttpURLConnectionHandler;
import com.canoo.platform.core.DolphinRuntimeException;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class HttpRequestImpl implements HttpRequest {

    private final HttpURLConnection connection;

    private final Gson gson;

    private final AtomicBoolean sent = new AtomicBoolean(false);

    private final List<HttpURLConnectionHandler> requestHandlers;

    private final List<HttpURLConnectionHandler> responseHandlers;

    private ByteArrayProvider dataProvider = new ByteArrayProvider() {
        @Override
        public byte[] get() {
            return new byte[0];
        }
    };

    public HttpRequestImpl(final HttpURLConnection connection, final Gson gson, final List<HttpURLConnectionHandler> requestHandlers, final List<HttpURLConnectionHandler> responseHandlers) {
        this.connection = Assert.requireNonNull(connection, "connection");
        this.gson = Assert.requireNonNull(gson, "gson");


        Assert.requireNonNull(requestHandlers, "requestHandlers");
        this.requestHandlers = Collections.unmodifiableList(requestHandlers);

        Assert.requireNonNull(responseHandlers, "responseHandlers");
        this.responseHandlers = Collections.unmodifiableList(responseHandlers);
    }

    @Override
    public HttpResponse withContent(final byte[] content) throws IOException {
        connection.setDoOutput(true);
        dataProvider = new ByteArrayProvider() {
            @Override
            public byte[] get() {
                return content;
            }
        };
        return withoutContent();
    }

    @Override
    public HttpResponse withContent(final String content) throws IOException {
        return withContent(content.getBytes(PlatformConstants.CHARSET));
    }

    @Override
    public <I> HttpResponse withContent(final I content) throws IOException {
        return withContent(gson.toJson(content));
    }

    @Override
    public HttpResponse withoutContent() throws IOException {
        if(sent.get()) {
            throw new DolphinRuntimeException("Request already sent");
        }
        sent.set(true);
        for(HttpURLConnectionHandler handler : requestHandlers) {
            handler.handle(connection);
        }
        return new HttpResponseImpl(connection, gson, dataProvider, responseHandlers);
    }

}
