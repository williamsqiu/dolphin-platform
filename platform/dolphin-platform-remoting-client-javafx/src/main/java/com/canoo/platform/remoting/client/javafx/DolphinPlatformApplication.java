/*
 * Copyright 2015-2017 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.platform.remoting.client.javafx;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.SimpleDolphinPlatformThreadFactory;
import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.client.PlatformClient;
import com.canoo.platform.core.DolphinRuntimeException;
import com.canoo.platform.core.PlatformThreadFactory;
import com.canoo.platform.remoting.DolphinRemotingException;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ClientContextFactory;
import com.canoo.platform.remoting.client.ClientInitializationException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apiguardian.api.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.apiguardian.api.API.Status.MAINTAINED;

/**
 * Defines a basic application class for Dolphin Platform based applications that can be used like the {@link Application}
 * class. Next to the general {@link Application} class of JavaFX this class supports the Dolphin Platform connection lifecycle.
 */
@API(since = "0.x", status = MAINTAINED)
public abstract class DolphinPlatformApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(DolphinPlatformApplication.class);

    private ClientContext clientContext;

    private ClientInitializationException initializationException;

    private Stage primaryStage;

    /**
     * Returns the server url of the Dolphin Platform server endpoint.
     *
     * @return the server url
     */
    protected abstract URL getServerEndpoint() throws MalformedURLException;

    private final ClientContext createClientContext() throws Exception {
        final ClientContextFactory factory = PlatformClient.getService(ClientContextFactory.class);
        final ClientConfiguration configuration = PlatformClient.getClientConfiguration();
        final URI endpoint = getServerEndpoint().toURI();
        final ClientContext clientContext = factory.create(configuration, endpoint);
        clientContext.addRemotingExceptionHandler(e -> onRemotingError(primaryStage, e));
        return clientContext;
    }

    /**
     * Creates the connection to the Dolphin Platform server. If this method will be overridden always call the super method.
     *
     * @throws Exception a exception if the connection can't be created
     */
    @Override
    public final void init() throws Exception {
        FxToolkit.init();

        applicationInit();

        final PlatformThreadFactory threadFactory = new SimpleDolphinPlatformThreadFactory();
        threadFactory.setUncaughtExceptionHandler((t, e) -> {
            if(e instanceof DolphinRuntimeException) {
                Platform.runLater(() -> onRuntimeError(primaryStage, (DolphinRuntimeException) e));
            } else {
                DolphinRuntimeException runtimeException = new DolphinRuntimeException("Unexpected error!", e);
                Platform.runLater(() -> onRuntimeError(primaryStage, runtimeException));
            }
        });
        final ClientConfiguration clientConfiguration = PlatformClient.getClientConfiguration();
        clientConfiguration.setBackgroundExecutor(Executors.newCachedThreadPool(threadFactory));

        try {
            clientContext = createClientContext();
            Assert.requireNonNull(clientContext, "clientContext");
            clientContext.connect().get(3_000, TimeUnit.MILLISECONDS);
        } catch (ClientInitializationException e) {
            initializationException = e;
        } catch (Exception e) {
            initializationException = new ClientInitializationException("Can not initialize Dolphin Platform Context", e);
        }
    }

    protected void applicationInit() throws Exception {

    }

    /**
     * This method must be defined by each application to create the initial view. The method will be called on
     * the JavaFX Platform thread after the connection to the DOlphin Platform server has been created.
     *
     * @param primaryStage  the primary stage
     * @param clientContext the Dolphin Platform context
     * @throws Exception in case of an error
     */
    protected abstract void start(Stage primaryStage, ClientContext clientContext) throws Exception;

    /**
     * This methods defines parts of the Dolphin Platform lifecyycle and is therefore defined as final.
     * Use the {@link DolphinPlatformApplication#start(Stage, ClientContext)} method instead.
     *
     * @param primaryStage the primary stage
     * @throws Exception in case of an error
     */
    @Override
    public final void start(final Stage primaryStage) throws Exception {
        Assert.requireNonNull(primaryStage, "primaryStage");

        this.primaryStage = primaryStage;

        if (initializationException == null) {
            if (clientContext != null) {
                try {
                    start(primaryStage, clientContext);
                } catch (Exception e) {
                    onRuntimeError(primaryStage, new ClientInitializationException("Error in application start!", e));
                }
            } else {
                onRuntimeError(primaryStage, new ClientInitializationException("No clientContext was created!"));
            }
        } else {
            onRuntimeError(primaryStage, initializationException);
        }
    }

    protected final CompletableFuture<Void> disconnect() {
        if (clientContext != null) {
            return clientContext.disconnect();
        } else {
            CompletableFuture<Void> result = new CompletableFuture<>();
            result.complete(null);
            return result;
        }
    }

    /**
     * Whenever JavaFX calls the stop method the connection to the Dolphin Platform server will be closed.
     *
     * @throws Exception an error
     */
    @Override
    public final void stop() throws Exception {
        disconnect();
    }

    protected final CompletableFuture<Void> reconnect(final Stage primaryStage) {
        Assert.requireNonNull(primaryStage, "primaryStage");
        final CompletableFuture<Void> result = new CompletableFuture<>();

        PlatformClient.getClientConfiguration().getBackgroundExecutor().execute(() -> {
            try {
                disconnect().get(1_000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                LOG.warn("Can not disconnect. Trying to reconnect anyway.");
            }

            try {
                if (clientContext == null) {
                    clientContext = createClientContext();
                }
                Assert.requireNonNull(clientContext, "clientContext");

                clientContext.connect().get(3_000, TimeUnit.MILLISECONDS);

                Platform.runLater(() -> {
                    try {
                        start(primaryStage, clientContext);
                    } catch (Exception e) {
                        onRuntimeError(primaryStage, new DolphinRuntimeException("Error in application reconnect", e));
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> onRuntimeError(primaryStage, new DolphinRuntimeException("Error in application reconnect", e)));
            }
            result.complete(null);
        });
        return result;
    }

    /**
     * This method is called if the connection to the Dolphin Platform server throws an exception at runtime. This can
     * for example happen if the server is shut down while the client is still running or if the server responses with
     * an error code.
     *
     * @param primaryStage     the primary stage
     * @param runtimeException the exception
     */
    protected void onRuntimeError(final Stage primaryStage, final DolphinRuntimeException runtimeException) {
        Assert.requireNonNull(runtimeException, "runtimeException");
        LOG.error("Dolphin Platform runtime error in thread " + runtimeException.getThread().getName(), runtimeException);
        Platform.exit();
    }

    protected void onRemotingError(final Stage primaryStage, final DolphinRemotingException exception) {
        Assert.requireNonNull(exception, "exception");
        LOG.error("Remoting error!", exception);
        Platform.exit();
    }
}
