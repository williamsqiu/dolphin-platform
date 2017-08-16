package com.canoo.platform.client;

import com.canoo.platform.core.functional.Function;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public interface HttpClient {

    void addHandler(HttpURLConnectionHandler handler);

    void removeHandler(HttpURLConnectionHandler handler);

    <R> R handleRequest(URL url, Function<HttpURLConnection, R> requestHandler) throws IOException;

    JsonElement doJsonRequest(URL url, RequestMethod method) throws IOException;

    JsonElement doJsonRequest(URL url, RequestMethod method, JsonElement data) throws IOException;

    <R> R doJsonRequest(URL url, RequestMethod method, Class<R> responseType) throws IOException;

    <I> void doJsonRequest(URL url, RequestMethod method, I data) throws IOException;

    <I, R> R doJsonRequest(URL url, RequestMethod method, I data, Class<R> responseType) throws IOException;

}
