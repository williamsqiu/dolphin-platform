package org.opendolphin.core.client;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class ClientPresentationModelTest {

    @Test
    public void testStandardCtor() {
        ClientPresentationModel model = new ClientPresentationModel("x", Collections.<ClientAttribute>emptyList());
        Assert.assertEquals("x", model.getId());
    }

    @Test
    public void testNullIdCtor() {
        ClientPresentationModel model1 = new ClientPresentationModel(Collections.<ClientAttribute>emptyList());
        ClientPresentationModel model2 = new ClientPresentationModel(Collections.<ClientAttribute>emptyList());
        Assert.assertNotEquals(model1.getId(), model2.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadIdCtor() {
        new ClientPresentationModel("1000-AUTO-CLT", Collections.<ClientAttribute>emptyList());
    }

}
