package com.canoo.dolphin.server.event;

import com.canoo.dolphin.server.DolphinSession;
import com.canoo.dolphin.server.context.DolphinSessionLifecycleHandler;
import com.canoo.dolphin.server.context.DolphinSessionProvider;
import org.testng.annotations.Test;

public class DistributedEventBusTests {

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullHazelcastClientNotAllowed() {
        DolphinSessionLifecycleHandler lifecycleHandler = new DolphinSessionLifecycleHandler();
        DolphinSessionProvider sessionProvider = new DolphinSessionProvider() {
            @Override
            public DolphinSession getCurrentDolphinSession() {
                return null;
            }
        };
        new DistributedEventBus(null, sessionProvider, lifecycleHandler);
    }

}
