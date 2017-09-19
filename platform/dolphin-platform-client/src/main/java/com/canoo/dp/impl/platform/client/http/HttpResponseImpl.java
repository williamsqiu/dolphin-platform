package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.http.HttpResponse;
import com.canoo.platform.client.http.HttpURLConnectionHandler;
import com.canoo.platform.core.DolphinRuntimeException;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.canoo.dp.impl.platform.core.PlatformConstants.ACCEPT_CHARSET_HEADER;
import static com.canoo.dp.impl.platform.core.PlatformConstants.ACCEPT_HEADER;
import static com.canoo.dp.impl.platform.core.PlatformConstants.CHARSET;
import static com.canoo.dp.impl.platform.core.PlatformConstants.JSON_MIME_TYPE;

public class HttpResponseImpl implements HttpResponse {

    private final HttpURLConnection connection;

    private final Gson gson;

    private final ByteArrayProvider dataProvider;

    private AtomicBoolean handled = new AtomicBoolean(false);

    private final List<HttpURLConnectionHandler> responseHandlers;

    public HttpResponseImpl(final HttpURLConnection connection, Gson gson, ByteArrayProvider dataProvider, List<HttpURLConnectionHandler> responseHandlers) {
        this.connection = Assert.requireNonNull(connection, "connection");
        this.gson = Assert.requireNonNull(gson, "gson");
        this.dataProvider = Assert.requireNonNull(dataProvider, "dataProvider");

        Assert.requireNonNull(responseHandlers, "responseHandlers");
        this.responseHandlers = Collections.unmodifiableList(responseHandlers);
    }

    @Override
    public byte[] readBytes() throws IOException {
        connection.setDoInput(true);

        handle();

        final InputStream is = connection.getInputStream();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int read = is.read();
        while (read != -1) {
            byteArrayOutputStream.write(read);
            read = is.read();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public String readString() throws IOException {
        connection.setRequestProperty(ACCEPT_CHARSET_HEADER, CHARSET);
        return new String(readBytes(), CHARSET);
    }

    @Override
    public <R> R readObject(Class<R> responseType) throws IOException {
        connection.setRequestProperty(ACCEPT_CHARSET_HEADER, CHARSET);
        connection.setRequestProperty(ACCEPT_HEADER, JSON_MIME_TYPE);
        return gson.fromJson(new String(readBytes(), CHARSET), responseType);
    }

    @Override
    public void handle() throws IOException {
        if(handled.get()) {
            throw new DolphinRuntimeException("Http call already handled");
        }
        handled.set(true);
        final byte[] data = dataProvider.get();
        if (data != null && data.length > 0) {
            final OutputStream w = connection.getOutputStream();
            w.write(data);
            w.close();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpStatus.HTTP_OK) {
            throw new DolphinRuntimeException("Bad response: " + responseCode);
        }

        for(HttpURLConnectionHandler handler : responseHandlers) {
            handler.handle(connection);
        }

    }
}
