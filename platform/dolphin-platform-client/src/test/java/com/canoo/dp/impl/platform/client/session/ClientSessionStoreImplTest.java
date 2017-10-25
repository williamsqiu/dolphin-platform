package com.canoo.dp.impl.platform.client.session;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;

public class ClientSessionStoreImplTest {
    ClientSessionStoreImpl implement;

    private static final String PROTOCOL = "http";
    private static final String HOST = "example.com";
    private static final int PORT = 80;
    private static final String ID = "dolphin";

    @Test
    public void getClientIdentifierForUrlWhenGivenAURL() throws Exception {
        //GIVEN
        final ClientSessionStoreImpl implement = new ClientSessionStoreImpl();
        URL url = new URL(PROTOCOL, HOST, PORT, "");
        implement.setClientIdentifierForUrl(url,"dolphin");
        //when
        final String result = implement.getClientIdentifierForUrl(url);
        //then

        Assert.assertEquals(result, "dolphin");
    }

    @Test
    public void setClientIdentifierForUrl() throws Exception {
        //GIVEN
        final ClientSessionStoreImpl implement = new ClientSessionStoreImpl();
        //when
        URL url = new URL(PROTOCOL, HOST, PORT, "");
        implement.setClientIdentifierForUrl(url, ID);
        //then
        final String setUrl = implement.getClientIdentifierForUrl(url);
        Assert.assertEquals(setUrl, ID);

    }

    @Test(expectedExceptions = NullPointerException.class)
    public void TestgetClientIdentifierForUrlWhenGivenANullUrl() throws Exception {
        implement.getClientIdentifierForUrl(null);
    }

    @Test
    public void TestSessionIsReset() throws Exception {
        //GIVEN
        final ClientSessionStoreImpl implement = new ClientSessionStoreImpl();
        //when
        URL url = new URL(PROTOCOL, HOST, PORT, "");
        implement.resetSession(url);

        //then
        final String setUrl = implement.getClientIdentifierForUrl(url);
        Assert.assertEquals(setUrl, null);

    }
}
