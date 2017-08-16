package com.canoo.dp.impl.platform.client;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.ClientSessionSupport;
import com.canoo.platform.client.HttpURLConnectionFactory;
import com.canoo.platform.client.HttpURLConnectionHandler;
import com.canoo.platform.client.RequestMethod;
import com.canoo.platform.client.UrlToAppDomainConverter;
import com.canoo.platform.core.DolphinRuntimeException;
import com.canoo.platform.core.functional.Function;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.canoo.dp.impl.platform.core.PlatformConstants.*;

public class ClientSessionSupportImpl implements ClientSessionSupport {

    private final ClientSessionStore clientSessionStore;

    private final HttpURLConnectionFactory httpURLConnectionFactory;

    private final HttpURLConnectionHandler requestHandler;

    private final HttpURLConnectionHandler responseHandler;

    private final List<HttpURLConnectionHandler> requestHandlers = new CopyOnWriteArrayList<>();

    private final Gson gson = new Gson();

    public ClientSessionSupportImpl() {
        this(new DefaultHttpURLConnectionFactory(), new SimpleUrlToAppDomainConverter());
    }

    public ClientSessionSupportImpl(final HttpURLConnectionFactory httpURLConnectionFactory) {
        this(httpURLConnectionFactory, new SimpleUrlToAppDomainConverter());
    }

    public ClientSessionSupportImpl(final UrlToAppDomainConverter converter) {
        this(new DefaultHttpURLConnectionFactory(), converter);
    }

    public ClientSessionSupportImpl(final HttpURLConnectionFactory httpURLConnectionFactory, final UrlToAppDomainConverter converter) {
        this.httpURLConnectionFactory = Assert.requireNonNull(httpURLConnectionFactory, "httpURLConnectionFactory");
        this.clientSessionStore = new ClientSessionStore(converter);
        this.requestHandler = new ClientSessionSupportingURLConnectionRequestHandler(clientSessionStore);
        this.responseHandler = new ClientSessionSupportingURLConnectionResponseHandler(clientSessionStore);
    }

    public void addHandler(HttpURLConnectionHandler handler) {
        requestHandlers.add(handler);
    }

    public void removeHandler(HttpURLConnectionHandler handler) {
        requestHandlers.remove(handler);
    }

    private synchronized HttpURLConnection createConnectionForRequest(final URL url) throws IOException {
        final HttpURLConnection connection = httpURLConnectionFactory.create(url);
        requestHandler.handle(connection);
        for (HttpURLConnectionHandler handler : requestHandlers) {
            handler.handle(connection);
        }
        return connection;
    }

    private synchronized void handleResponse(final HttpURLConnection response) {
        responseHandler.handle(response);
    }

    public synchronized void closeClientSession(final URL url) {
        clientSessionStore.resetSession(url);
    }

    public <R> R handleRequest(final URL url, final Function<HttpURLConnection, R> function) throws IOException {
        Assert.requireNonNull(function, "function");
        final HttpURLConnection connection = createConnectionForRequest(url);
        try {
            return function.call(connection);
        } finally {
            handleResponse(connection);
        }
    }

    @Override
    public JsonElement doJsonRequest(URL url, RequestMethod method) throws IOException {
        return doJsonRequest(url, method, JsonNull.INSTANCE);
    }

    @Override
    public <R> R doJsonRequest(URL url, RequestMethod method, Class<R> responseType) throws IOException {
        return doJsonRequest(url, method, null, responseType);
    }

    @Override
    public <I> void doJsonRequest(URL url, RequestMethod method, I data) throws IOException {
        doJsonRequest(url, method, data, null);
    }

    @Override
    public <I, R> R doJsonRequest(URL url, RequestMethod method, I data, Class<R> responseType) throws IOException {
        return gson.fromJson(doJsonRequest(url, method, gson.toJsonTree(data)), responseType);
    }

    public JsonElement doJsonRequest(URL url, RequestMethod method, JsonElement data) throws IOException {
        return handleRequest(url, c -> {
            try {
                //REQUEST
                if (data != null) {
                    c.setDoOutput(true);
                }
                c.setDoInput(true);
                c.setRequestProperty(ACCEPT_CHARSET_HEADER, CHARSET);
                c.setRequestProperty(CONTENT_TYPE_HEADER, JSON_MIME_TYPE);
                c.setRequestProperty(ACCEPT_HEADER, JSON_MIME_TYPE);
                c.setRequestMethod(method.getRawName());
                if (data != null) {
                    final String content = gson.toJson(data);
                    final OutputStream w = c.getOutputStream();
                    w.write(content.getBytes(CHARSET));
                    w.close();
                }

                //RESPONSE
                int responseCode = c.getResponseCode();
                if (responseCode != HttpStatus.HTTP_OK) {
                    throw new DolphinRuntimeException("Bad response: " + responseCode);
                }
                String receivedContent = new String(inputStreamToByte(c.getInputStream()), CHARSET);
                JsonParser parser = new JsonParser();
                return parser.parse(receivedContent);
            } catch (Exception e) {
                throw new RuntimeException("Error in communication", e);
            }
        });
    }


    private byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int read = is.read();
        while (read != -1) {
            byteArrayOutputStream.write(read);
            read = is.read();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public String getClientId(URL url) {
        return clientSessionStore.getClientIdentifierForUrl(url);
    }
}
