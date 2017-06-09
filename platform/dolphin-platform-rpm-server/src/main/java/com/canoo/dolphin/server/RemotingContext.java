package com.canoo.dolphin.server;

import com.canoo.dolphin.BeanManager;
import com.canoo.dolphin.server.binding.PropertyBinder;
import com.canoo.dolphin.server.event.DolphinEventBus;

public interface RemotingContext {

    String getId();

    ClientSessionExecutor createSessionExecutor();

    PropertyBinder getBinder();

    BeanManager getBeanManager();

    DolphinEventBus getEventBus();
}
