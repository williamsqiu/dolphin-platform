package com.canoo.platform.client.spi;

import com.canoo.platform.client.ClientConfiguration;

public interface ServiceProvider<S> {

    S getService(ClientConfiguration configuration);

    Class<S> getServiceType();
}
