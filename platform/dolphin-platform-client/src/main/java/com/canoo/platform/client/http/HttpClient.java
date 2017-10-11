package com.canoo.platform.client.http;

import java.net.URL;

public interface HttpClient {

    void addResponseHandler(final HttpURLConnectionHandler handler);

    void addRequestHandler(final HttpURLConnectionHandler handler);

    HttpRequest request(URL url, RequestMethod method);

    HttpRequest request(String url, RequestMethod method);

    HttpRequest request(URL url);

    HttpRequest request(String url);
}
