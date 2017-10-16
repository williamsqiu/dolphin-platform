package com.canoo.platform.remoting.client;

import com.canoo.platform.client.ClientConfiguration;

import java.net.URL;

/**
 * Created by hendrikebbers on 11.10.17.
 */
public interface ClientContextFactory {

    ClientContext create(final ClientConfiguration clientConfiguration, final URL endpoint);
}
