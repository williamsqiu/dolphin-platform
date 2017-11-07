package com.canoo.dolphin.integration;

import com.canoo.platform.remoting.client.ClientContext;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ClientIdTest extends AbstractIntegrationTest {

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER)
    public void testThatClientIdIsNotNull(String containerType, String endpoint) {
        ClientContext context = connect(endpoint);
        Assert.assertNotNull(context.getClientId());
    }

}
