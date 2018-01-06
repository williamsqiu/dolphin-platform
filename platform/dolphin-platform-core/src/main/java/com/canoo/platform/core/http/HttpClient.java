package com.canoo.platform.core.http;

import org.apiguardian.api.API;

import java.net.URI;

import static com.canoo.platform.core.http.RequestMethod.DELETE;
import static com.canoo.platform.core.http.RequestMethod.GET;
import static com.canoo.platform.core.http.RequestMethod.POST;
import static com.canoo.platform.core.http.RequestMethod.PUT;
import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
public interface HttpClient {

    @Deprecated
    void addResponseHandler(final HttpURLConnectionHandler handler);

    @Deprecated
    default HttpCallRequestBuilder request(URI url) {
        return get(url);
    }

    @Deprecated
    default HttpCallRequestBuilder request(String url) {
        return get(url);
    }

    HttpCallRequestBuilder request(URI url, RequestMethod method);

    HttpCallRequestBuilder request(String url, RequestMethod method);

    default HttpCallRequestBuilder get(final URI url) {
        return request(url, GET);
    }

    default HttpCallRequestBuilder get(final String url) {
        return request(url, GET);
    }

    default HttpCallRequestBuilder post(final URI url) {
        return request(url, POST);
    }

    default HttpCallRequestBuilder post(final String url) {
        return request(url, POST);
    }

    default HttpCallRequestBuilder put(final URI url) {
        return request(url, PUT);
    }

    default HttpCallRequestBuilder put(final String url) {
        return request(url, PUT);
    }

    default HttpCallRequestBuilder delete(final URI url) {
        return request(url, DELETE);
    }

    default HttpCallRequestBuilder delete(final String url) {
        return request(url, DELETE);
    }
}
