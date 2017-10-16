package com.canoo.platform.client.spi;

import com.canoo.platform.client.ClientConfiguration;
import com.canoo.platform.core.framework.Incubating;

@Incubating("0.19.0")
public interface ServiceProvider<S> {

    S getService(ClientConfiguration configuration);

    Class<S> getServiceType();
}
