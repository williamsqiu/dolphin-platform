package com.canoo.platform.remoting.client;

import org.apiguardian.api.API;

import java.util.concurrent.CompletableFuture;

import static org.apiguardian.api.API.Status.MAINTAINED;

@API(since = "0.x", status = MAINTAINED)
public interface ControllerFactory {

    <T> CompletableFuture<ControllerProxy<T>> createController(String name);

}
