package com.canoo.dolphin.server.event;

import org.testng.annotations.Test;

public class DistributedEventBusTests {

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullHazelcastClientNotAllowed() {
        new DistributedEventBus(null);
    }

}
