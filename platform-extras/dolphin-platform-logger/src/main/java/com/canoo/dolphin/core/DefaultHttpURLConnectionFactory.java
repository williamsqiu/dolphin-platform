package com.canoo.dolphin.core;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.http.HttpURLConnectionFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@Deprecated
public class DefaultHttpURLConnectionFactory implements HttpURLConnectionFactory {

    @Override
    public HttpURLConnection create(final URL url) throws IOException {
        Assert.requireNonNull(url, "url");
        final URLConnection connection = url.openConnection();
        if(connection instanceof HttpURLConnection) {
            return (HttpURLConnection) connection;
        }
        throw new IOException("URL do not provide a HttpURLConnection!");
    }
}
