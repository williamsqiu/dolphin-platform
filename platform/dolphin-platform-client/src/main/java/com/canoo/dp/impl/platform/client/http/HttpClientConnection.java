package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.http.HttpHeader;
import com.canoo.platform.core.http.HttpURLConnectionFactory;
import com.canoo.platform.core.http.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.canoo.platform.core.http.RequestMethod.GET;

public class HttpClientConnection {

    private final static Logger LOG = LoggerFactory.getLogger(HttpClientConnection.class);

    private final HttpURLConnection connection;

    private final RequestMethod method;

    private final URI url;

    public HttpClientConnection(final HttpURLConnectionFactory httpURLConnectionFactory, final URI url, final RequestMethod method) throws IOException {
        Assert.requireNonNull(httpURLConnectionFactory, "httpURLConnectionFactory");
        this.url = Assert.requireNonNull(url, "url");
        this.method = Assert.requireNonNull(method, "method");

        this.connection = httpURLConnectionFactory.create(url);
        if (connection == null) {
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
            if (method.equals(GET)) {
                LOG.warn("You are currently defining a request content for a HTTP GET call for endpoint '{}'", url);
            }
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
        final InputStream errorstream = connection.getErrorStream();

        if(errorstream == null) {
            try (final InputStream inputStream = connection.getInputStream()) {
                try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                    int read = inputStream.read();
                    while (read != -1) {
                        byteArrayOutputStream.write(read);
                        read = inputStream.read();
                    }
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } else {
            try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                int read = errorstream.read();
                while (read != -1) {
                    byteArrayOutputStream.write(read);
                    read = errorstream.read();
                }
                return byteArrayOutputStream.toByteArray();
            } finally {
                errorstream.close();
            }
        }
    }

    public String getResponseMessage() throws IOException {
        return connection.getResponseMessage();
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
