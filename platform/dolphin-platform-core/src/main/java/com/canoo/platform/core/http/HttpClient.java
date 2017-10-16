package com.canoo.platform.core.http;

import java.net.URL;

public interface HttpClient {

    @Deprecated
    void addResponseHandler(final HttpURLConnectionHandler handler);

    @Deprecated
    void addRequestHandler(final HttpURLConnectionHandler handler);

    HttpRequest request(URL url, RequestMethod method);

    HttpRequest request(String url, RequestMethod method);

    HttpRequest request(URL url);

    HttpRequest request(String url);
}
