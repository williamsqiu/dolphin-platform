package com.canoo.platform.remoting.client;

import com.canoo.platform.client.ClientConfiguration;
import org.apiguardian.api.API;

import java.net.URI;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(since = "0.x", status = EXPERIMENTAL)
public interface ClientContextFactory {

    ClientContext create(final ClientConfiguration clientConfiguration, final URI endpoint);
}
