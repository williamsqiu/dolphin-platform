package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.core.DolphinRuntimeException;
import com.canoo.platform.core.http.ByteArrayProvider;
import com.canoo.platform.core.http.HttpCallExecutor;
import com.canoo.platform.core.http.HttpCallResponseBuilder;
import com.canoo.platform.core.http.HttpHeader;
import com.canoo.platform.core.http.HttpResponse;
import com.canoo.platform.core.http.HttpURLConnectionHandler;
import com.google.gson.Gson;
import org.apiguardian.api.API;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.canoo.dp.impl.platform.core.PlatformConstants.ACCEPT_CHARSET_HEADER;
import static com.canoo.dp.impl.platform.core.PlatformConstants.ACCEPT_HEADER;
import static com.canoo.dp.impl.platform.core.PlatformConstants.CHARSET;
import static com.canoo.dp.impl.platform.core.PlatformConstants.JSON_MIME_TYPE;
import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class HttpCallResponseBuilderImpl implements HttpCallResponseBuilder {

    private final HttpClientConnection connection;

    private final Gson gson;

    private final AtomicBoolean handled = new AtomicBoolean(false);

    private final List<HttpURLConnectionHandler> requestHandlers;

    private final List<HttpURLConnectionHandler> responseHandlers;

    private final ByteArrayProvider dataProvider;

    private final ClientConfiguration configuration;

    public HttpCallResponseBuilderImpl(final HttpClientConnection connection, final ByteArrayProvider dataProvider, final Gson gson, final List<HttpURLConnectionHandler> requestHandlers, final List<HttpURLConnectionHandler> responseHandlers, ClientConfiguration configuration) {
        this.connection = Assert.requireNonNull(connection, "connection");
        this.dataProvider = Assert.requireNonNull(dataProvider, "dataProvider");
        this.gson = Assert.requireNonNull(gson, "gson");
        this.configuration = Assert.requireNonNull(configuration, "configuration");

        Assert.requireNonNull(requestHandlers, "requestHandlers");
        this.requestHandlers = Collections.unmodifiableList(requestHandlers);

        Assert.requireNonNull(responseHandlers, "responseHandlers");
        this.responseHandlers = Collections.unmodifiableList(responseHandlers);
    }

    @Override
    public HttpCallExecutor<ByteArrayProvider> readBytes() {
        final ResponseContentConverter<ByteArrayProvider> converter = b -> new SimpleByteArrayProvider(connection.readResponseContent());
        return createExecutor(converter);
    }

    @Override
    public HttpCallExecutor<String> readString() {
        connection.addRequestHeader(new HttpHeaderImpl(ACCEPT_CHARSET_HEADER, CHARSET));

        final ResponseContentConverter<String> converter = b -> new String(connection.readResponseContent(), CHARSET);
        return createExecutor(converter);
    }

    @Override
    public <R> HttpCallExecutor<R> readObject(final Class<R> responseType) {
        Assert.requireNonNull(responseType, "responseType");

        connection.addRequestHeader(new HttpHeaderImpl(ACCEPT_CHARSET_HEADER, CHARSET));
        connection.addRequestHeader(new HttpHeaderImpl(ACCEPT_HEADER, JSON_MIME_TYPE));

        final ResponseContentConverter<R> converter = b -> gson.fromJson(new String(b, CHARSET), responseType);
        return createExecutor(converter);
    }

    @Override
    public HttpCallExecutor<Void> withoutResult() {
        final ResponseContentConverter<Void> converter = b -> null;
        return createExecutor(converter);
    }

    @Override
    public HttpCallExecutor<ByteArrayProvider> readBytes(final String contentType) {
        connection.addRequestHeader(new HttpHeaderImpl(ACCEPT_HEADER, contentType));
        return readBytes();
    }

    @Override
    public HttpCallExecutor<String> readString(final String contentType) {
        Assert.requireNonNull(contentType, "contentType");

        connection.addRequestHeader(new HttpHeaderImpl(ACCEPT_HEADER, contentType));
        return readString();
    }

    private <R> HttpCallExecutor<R> createExecutor(final ResponseContentConverter<R> converter) {
        return new HttpCallExecutorImpl<>(configuration, () -> handleRequest(converter));
    }

    private <R> HttpResponse<R> handleRequest(final ResponseContentConverter<R> converter) throws Exception {
        if (handled.get()) {
            throw new DolphinRuntimeException("Http call already handled");
        }
        handled.set(true);

        requestHandlers.forEach(h -> h.handle(connection.getConnection()));
        connection.writeRequestContent(dataProvider.get());
        final int responseCode = connection.readResponseCode();
        responseHandlers.forEach(h -> h.handle(connection.getConnection()));

        final byte[] rawContent = connection.readResponseContent();
        final R content = converter.convert(rawContent);
        final List<HttpHeader> headers = connection.getResponseHeaders();

        return new HttpResponseImpl<R>(headers, responseCode, rawContent, content);

    }

}
