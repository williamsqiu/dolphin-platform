package com.canoo.impl.dp.spring.test;

import com.canoo.dp.impl.client.ClientContextImpl;
import com.canoo.dp.impl.client.legacy.ClientModelStore;
import com.canoo.dp.impl.client.legacy.communication.AbstractClientConnector;
import com.canoo.dp.impl.platform.client.session.ClientSessionStoreImpl;
import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.server.context.DolphinContext;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.core.http.HttpCallRequestBuilder;
import com.canoo.platform.core.http.HttpClient;
import com.canoo.platform.core.http.HttpURLConnectionHandler;
import com.canoo.platform.core.http.RequestMethod;
import com.canoo.platform.remoting.client.RemotingExceptionHandler;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestClientContextImpl extends ClientContextImpl implements TestClientContext {

    private final DolphinContext dolphinServerContext;

    public TestClientContextImpl(final ClientConfiguration clientConfiguration, final URI endpoint, final DolphinContext dolphinServerContext) {
        super(clientConfiguration, endpoint, new InternalHttpClient(), new ClientSessionStoreImpl());
        this.dolphinServerContext = Assert.requireNonNull(dolphinServerContext, "dolphinServerContext");
    }

    @Override
    protected AbstractClientConnector createConnector(final ClientConfiguration configuration, final ClientModelStore modelStore, final HttpClient httpClient, final RemotingExceptionHandler connectorExceptionHandler) {
        return new DolphinTestClientConnector(modelStore, Executors.newSingleThreadExecutor(), c -> dolphinServerContext.handle(c));
    }

    @Override
    public void sendPing() {
        try {
            getDolphinCommandHandler().invokeDolphinCommand(new PingCommand()).get();
        } catch (Exception e) {
            throw new RuntimeException("Error in ping handling", e);
        }
    }

    @Override
    public void sendPing(long time, TimeUnit unit) {
        Assert.requireNonNull(unit, "unit");
        try {
            getDolphinCommandHandler().invokeDolphinCommand(new PingCommand()).get(time, unit);
        } catch (Exception e) {
            throw new RuntimeException("Error in ping handling", e);
        }
    }

    private static class InternalHttpClient implements HttpClient {

        @Override
        public void addResponseHandler(final HttpURLConnectionHandler handler) {
            throw new RuntimeException("HTTP connection sould not be used!");
        }

        @Override
        public HttpCallRequestBuilder request(final URI url, final RequestMethod method) {
            throw new RuntimeException("HTTP connection sould not be used!");
        }

        @Override
        public HttpCallRequestBuilder request(final String url, final RequestMethod method) {
            throw new RuntimeException("HTTP connection sould not be used!");
        }
    }
}
