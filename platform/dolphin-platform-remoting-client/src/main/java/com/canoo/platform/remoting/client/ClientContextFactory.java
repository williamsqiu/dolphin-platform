package com.canoo.platform.remoting.client;

import com.canoo.platform.client.ClientConfiguration;
import org.apiguardian.api.API;

import java.net.URI;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

/**
 * Factory to create a {@link ClientContext}. Normally you will create a {@link ClientContext} at the bootstrap of your
 * client by using the {@link #create(ClientConfiguration, URI)} method and use this context as a singleton in your client.
 * The {@link ClientContext} defines the connection between the client and the Dolphin Platform server endpoint.
 */
@API(since = "0.x", status = EXPERIMENTAL)
@FunctionalInterface
public interface ClientContextFactory {

    /**
     * Create a {@link ClientContext} based on the given configuration.
     *
     * @param clientConfiguration the configuration
     * @param endpoint the server endpoint
     *
     * @return the context
     */
    ClientContext create(final ClientConfiguration clientConfiguration, final URI endpoint);
}
