package com.canoo.dolphin.legacy;

import com.canoo.dp.impl.remoting.legacy.core.ModelStoreEvent;
import com.canoo.dp.impl.remoting.legacy.core.ModelStoreListener;
import com.canoo.dp.impl.remoting.legacy.core.ModelStoreListenerWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ModelStoreListenerWrapperTest {
    @Test
    public void testEquals() {

        //given:
        ModelStoreListener listener = new ModelStoreListener() {
            @Override
            public void modelStoreChanged(ModelStoreEvent event) {

            }

        };
        ModelStoreListenerWrapper wrapper = new ModelStoreListenerWrapper("no-type", listener);

        //then:
        Assert.assertEquals(new ModelStoreListenerWrapper("no-type", listener), wrapper);
        Assert.assertNotEquals(new ModelStoreListenerWrapper("other-type", listener), wrapper);
    }

}
