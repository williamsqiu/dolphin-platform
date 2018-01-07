package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.http.HttpHeader;
import com.canoo.platform.core.http.HttpURLConnectionFactory;
import com.canoo.platform.core.http.RequestMethod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class HttpClientConnection {

    private final HttpURLConnection connection;

    public HttpClientConnection(final HttpURLConnectionFactory httpURLConnectionFactory, final URI url, final RequestMethod method) throws IOException {
        Assert.requireNonNull(httpURLConnectionFactory, "httpURLConnectionFactory");
        Assert.requireNonNull(url, "url");
        Assert.requireNonNull(method, "method");

        this.connection = httpURLConnectionFactory.create(url);
        if(connection == null) {
            throw new IllegalStateException("Connection was not created. Check connection factory!");
        }
        this.connection.setRequestMethod(method.getRawName());
        connection.setUseCaches(false);
    }

    public void addRequestHeader(final HttpHeader headers) {
        Assert.requireNonNull(headers, "headers");
        connection.setRequestProperty(headers.getName(), headers.getContent());
    }

    public void writeRequestContent(final byte[] content) throws IOException {
        Assert.requireNonNull(content, "content");
        if (content.length > 0) {
            setDoOutput(true);
            try (final OutputStream w = connection.getOutputStream()) {
                w.write(content);
            }
        }
    }

    public int readResponseCode() throws IOException {
        return connection.getResponseCode();
    }

    public byte[] readResponseContent() throws IOException {
        try (final InputStream is = connection.getInputStream()) {
            try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                int read = is.read();
                while (read != -1) {
                    byteArrayOutputStream.write(read);
                    read = is.read();
                }
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public List<HttpHeader> getResponseHeaders() {
        return connection.getHeaderFields().
                entrySet().
                stream().
                flatMap(e -> e.getValue().stream().map(v -> new HttpHeaderImpl(e.getKey(), v))).
                collect(Collectors.toList());
    }

    public void setDoOutput(final boolean doOutput) {
        connection.setDoOutput(doOutput);
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

}
