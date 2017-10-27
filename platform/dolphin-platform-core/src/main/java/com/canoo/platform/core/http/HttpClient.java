package com.canoo.platform.core.http;

import org.apiguardian.api.API;

import java.net.URL;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
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
