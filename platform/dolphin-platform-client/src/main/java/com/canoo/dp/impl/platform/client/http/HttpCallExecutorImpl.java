package com.canoo.dp.impl.platform.client.http;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.core.DolphinRuntimeException;
import com.canoo.platform.core.http.HttpCallExecutor;
import com.canoo.platform.core.http.HttpResponse;
import org.apiguardian.api.API;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class HttpCallExecutorImpl<R> implements HttpCallExecutor<R> {

    private final ExecutorService executor;

    private final HttpProvider<R> provider;

    private final Executor uiExecutor;

    private Consumer<HttpResponse<R>> onDone;

    private BiConsumer<Throwable, HttpResponse<R>> errorHandler;

    public HttpCallExecutorImpl(final ClientConfiguration configuration, final HttpProvider<R> provider) {
        Assert.requireNonNull(configuration, "configuration");
        this.executor = configuration.getBackgroundExecutor();
        this.uiExecutor = configuration.getUiExecutor();
        this.provider = Assert.requireNonNull(provider, "provider");
    }

    @Override
    public HttpCallExecutor<R> onDone(final Consumer<HttpResponse<R>> onDone) {
        this.onDone = onDone;
        return this;
    }

    @Override
    public HttpCallExecutor<R> onError(BiConsumer<Throwable, HttpResponse<R>> errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    @Override
    public CompletableFuture<HttpResponse<R>> execute() {
        final CompletableFuture<HttpResponse<R>> completableFuture = new CompletableFuture<>();
        executor.submit(() -> {
            try {
                final HttpResponse<R> result = provider.get();
                final int statusCode = result.getStatusCode();
                if (statusCode >= 300) {
                    final Exception e = new DolphinRuntimeException("Bad Response: " + statusCode);
                    if (errorHandler != null) {
                        uiExecutor.execute(() -> errorHandler.accept(e, result));
                    }
                    completableFuture.completeExceptionally(e);
                } else {
                    if (onDone != null) {
                        uiExecutor.execute(() -> onDone.accept(result));
                    }
                    completableFuture.complete(result);
                }
            } catch (final Throwable e) {
                if (errorHandler != null) {
                    uiExecutor.execute(() -> errorHandler.accept(e, null));
                }
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }
}
