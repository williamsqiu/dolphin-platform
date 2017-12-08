package com.canoo.platform.remoting.client;

import org.apiguardian.api.API;

import java.util.concurrent.CompletableFuture;

import static org.apiguardian.api.API.Status.MAINTAINED;

@API(since = "0.x", status = MAINTAINED)
@FunctionalInterface
public interface ControllerFactory {

    /**
     * Creates a {@link ControllerProxy} instance for the controller with the given name.
     * By doing so a new instance of the matching controller class will be created on the server.
     * The {@link ControllerProxy} can be used to communicate with the controller instance on the
     * server. The method don't block. To get the created {@link ControllerProxy} instance {@link CompletableFuture#get()}
     * must be called on the return value.
     * @param name the unique name of the controller type
     * @param <T> the type of the model that is bound to the controller and view
     * @return a {@link CompletableFuture} that defines the creation of the controller.
     */
    <T> CompletableFuture<ControllerProxy<T>> createController(String name);

}
