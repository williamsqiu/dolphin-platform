package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.http.HttpExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class HttpExecutorImpl<R> implements HttpExecutor<R> {

    private final ExecutorService executor;

    private final HttpProvider<R> provider;

    private final Executor uiExecutor;

    private Consumer<R> onDone;

    private Consumer<Throwable> onError;

    public HttpExecutorImpl(final ClientConfiguration configuration, final HttpProvider<R> provider) {
        Assert.requireNonNull(configuration, "configuration");
        this.executor = configuration.getBackgroundExecutor();
        this.uiExecutor = configuration.getUiExecutor();
        this.provider = Assert.requireNonNull(provider, "provider");
    }

    @Override
    public HttpExecutor<R> onDone(final Consumer<R> onDone) {
        this.onDone = onDone;
        return this;
    }

    @Override
    public HttpExecutor<R> onError(final Consumer<Throwable> onError) {
        this.onError = onError;
        return this;
    }

    @Override
    public CompletableFuture<R> execute() {
        final CompletableFuture<R> completableFuture = new CompletableFuture<R>();
        executor.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    final R result = provider.get();
                    if(onDone != null) {
                        uiExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                onDone.accept(result);
                            }
                        });
                    }
                    completableFuture.complete(result);
                } catch (final Throwable e) {
                    if(onError != null) {
                        uiExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                onError.accept(e);
                            }
                        });
                    }
                    completableFuture.completeExceptionally(e);
                }
            }
        });
        return completableFuture;
    }
}
