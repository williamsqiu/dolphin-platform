package com.canoo.platform.core.http;

import org.apiguardian.api.API;

import java.net.URI;
import java.net.URL;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
public interface HttpClient {

    @Deprecated
    void addResponseHandler(final HttpURLConnectionHandler handler);

    @Deprecated
    void addRequestHandler(final HttpURLConnectionHandler handler);

    HttpRequest request(URI url, RequestMethod method);

    HttpRequest request(String url, RequestMethod method);

    HttpRequest request(URI url);

    HttpRequest request(String url);
}
