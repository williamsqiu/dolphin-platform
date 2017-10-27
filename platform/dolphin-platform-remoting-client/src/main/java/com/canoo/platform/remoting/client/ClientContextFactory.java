package com.canoo.platform.remoting.client;

import com.canoo.platform.client.ClientConfiguration;
import org.apiguardian.api.API;

import java.net.URL;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;
import static org.apiguardian.api.API.Status.INTERNAL;

/**
 * Created by hendrikebbers on 11.10.17.
 */
@API(since = "0.x", status = EXPERIMENTAL)
public interface ClientContextFactory {

    ClientContext create(final ClientConfiguration clientConfiguration, final URL endpoint);
}
