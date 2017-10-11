package com.canoo.platform.remoting.client;

/**
 * Created by hendrikebbers on 11.10.17.
 */
public interface ClientContextFactory {

    ClientContext create(final ClientConfiguration clientConfiguration);
}
