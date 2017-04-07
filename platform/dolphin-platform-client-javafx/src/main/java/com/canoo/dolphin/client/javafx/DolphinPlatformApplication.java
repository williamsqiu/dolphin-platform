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
package com.canoo.dolphin.client.javafx;

import com.canoo.dolphin.client.*;
import com.canoo.dolphin.util.Assert;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Defines a basic application class for Dolphin Platform based applications that can be used like the {@link Application}
 * class. Next to the general {@link Application} class of JavaFX this class supports the DOlphin Platform connecttion lifecycle.
 */
public abstract class DolphinPlatformApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(DolphinPlatformApplication.class);

    private ClientContext clientContext;

    private ClientInitializationException initializationException;

    private Stage primaryStage;

    /**
     * Creates the connection to the Dolphin Platform server. If this method will be overridden always call the super method.
     *
     * @throws Exception a exception if the connection can't be created
     */
    @Override
    public void init() throws Exception {
        try {
            ClientConfiguration clientConfiguration = getClientConfiguration();
            clientConfiguration.getDolphinPlatformThreadFactory().setUncaughtExceptionHandler((Thread thread, Throwable exception) -> {
                clientConfiguration.getUiExecutor().execute(() -> {
                    Assert.requireNonNull(thread, "thread");
                    Assert.requireNonNull(exception, "exception");
                    onRuntimeError(primaryStage, new DolphinRuntimeException(thread, "Unhandled error in Dolphin Platform background thread", exception));
                });
            });
            clientContext = ClientContextFactory.connect(clientConfiguration).handle(new BiFunction<ClientContext, Throwable, ClientContext>() {
                @Override
                public ClientContext apply(ClientContext clientContext, Throwable throwable) {
                    if(throwable != null) {
                        throw new RuntimeException("Error in creating client context", throwable);
                    }
                    return clientContext;
                }
            }).get(clientConfiguration.getConnectionTimeout(), TimeUnit.MILLISECONDS);
            clientContext.connect().get(clientConfiguration.getConnectionTimeout(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            initializationException = new ClientInitializationException("Can not initialize Dolphin Platform Context", e);
        }
    }

    protected CompletableFuture<Void> disconnect() {
        if(clientContext == null) {
            throw new IllegalStateException("Client context not defined");
        }
        return clientContext.disconnect();
    }

    protected CompletableFuture<Void> reconnect() {
        if(clientContext == null) {
            throw new IllegalStateException("Client context not defined");
        }
        return clientContext.reconnect();
    }

    protected CompletableFuture<Void> connect() {
        if(clientContext == null) {
            throw new IllegalStateException("Client context not defined");
        }
        return clientContext.connect();
    }

    /**
     * Returns the Dolphin Platform configuration for the client. As long as all the default configurations can be used
     * this method don't need to be overridden. The URL of the server will be configured by the {@link DolphinPlatformApplication#getServerEndpoint()}
     * method.
     *
     * @return The Dolphin Platform configuration for this client
     */
    protected JavaFXConfiguration getClientConfiguration() {
        JavaFXConfiguration configuration = null;
        try {
            configuration = new JavaFXConfiguration(getServerEndpoint());
            configuration.setRemotingExceptionHandler(e -> onRuntimeError(primaryStage, new DolphinRuntimeException("Dolphin Platform remoting error!", e)));
        } catch (MalformedURLException e) {
            throw new ClientInitializationException("Client configuration cannot be created", e);
        }
        return configuration;
    }

    /**
     * Returns the server url of the Dolphin Platform server endpoint.
     *
     * @return the server url
     */
    protected abstract URL getServerEndpoint() throws MalformedURLException;

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
                    onInitializationError(primaryStage, new ClientInitializationException("Error in application start!", e));
                }
            } else {
                onInitializationError(primaryStage, new ClientInitializationException("No clientContext was created!"));
            }
        } else {
            onInitializationError(primaryStage, initializationException);
        }
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
     * Whenever JavaFX calls the stop method the connection to the Dolphin Platform server will be closed.
     *
     * @throws Exception an error
     */
    @Override
    public final void stop() throws Exception {
        if (clientContext != null) {
            try {
                clientContext.disconnect();
            } catch (Exception e) {
                onShutdownError(new ClientShutdownException(e));
            }
        }
    }

    protected final void reconnect(Stage primaryStage) {

    }

    /**
     * This method is called if the connection to the Dolphin Platform server can't be created. Application developers
     * can define some kind of error handling here.
     * By default the methods prints the exception in the log an call {@link System#exit(int)}
     *
     * @param primaryStage            the primary stage
     * @param initializationException the exception
     */
    protected void onInitializationError(Stage primaryStage, ClientInitializationException initializationException) {
        Assert.requireNonNull(initializationException, "initializationException");
        LOG.error("Dolphin Platform initialization error", initializationException);
        Platform.exit();
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

    /**
     * This method is called if the connection to the Dolphin Platform server can't be closed on {@link Application#stop()}.
     * Application developers can define some kind of error handling here.
     * By default the methods prints the exception in the log an call {@link System#exit(int)}
     *
     * @param shutdownException
     */
    protected void onShutdownError(ClientShutdownException shutdownException) {
        Assert.requireNonNull(shutdownException, "shutdownException");
        LOG.error("Dolphin Platform shutdown error", shutdownException);
        System.exit(-1);
    }
}
