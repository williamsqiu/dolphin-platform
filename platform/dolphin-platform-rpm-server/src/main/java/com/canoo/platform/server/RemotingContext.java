package com.canoo.platform.server;

import com.canoo.dolphin.BeanManager;
import com.canoo.platform.server.binding.PropertyBinder;
import com.canoo.platform.server.event.DolphinEventBus;

public interface RemotingContext {

    String getId();

    ClientSessionExecutor createSessionExecutor();

    PropertyBinder getBinder();

    BeanManager getBeanManager();

    DolphinEventBus getEventBus();
}
