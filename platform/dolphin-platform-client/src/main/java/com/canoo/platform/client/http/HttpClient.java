package com.canoo.platform.client.http;

import java.io.IOException;
import java.net.URL;

public interface HttpClient {

    void addResponseHandler(final HttpURLConnectionHandler handler);

    void addRequestHandler(final HttpURLConnectionHandler handler);

    HttpRequest request(URL url, RequestMethod method) throws IOException;

}
