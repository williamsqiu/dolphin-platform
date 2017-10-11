package com.canoo.platform.remoting.client;

import java.util.concurrent.CompletableFuture;

public interface ControllerFactory {

    <T> CompletableFuture<ControllerProxy<T>> createController(String name);

}
