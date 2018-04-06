package com.canoo.impl.platform.core.context;

import com.canoo.dp.impl.platform.core.context.ContextImpl;
import com.canoo.platform.core.context.Context;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;

public class ContextImplTest {

    @Test(expectedExceptions = RuntimeException.class)
    public void testNullKey() {
        //given:
        final Context context1 = new ContextImpl(null, "SOME_VALUE");
    }

    @Test
    public void testNullValue() {
        //given:
        final Context context1 = new ContextImpl("KEY", null);

        //then:
        Assert.assertNull(context1.getValue());
    }

    @Test
    public void testEquals() {
        //given:
        final Context context1 = new ContextImpl("KEY", "SOME_VALUE");
        final Context context2 = new ContextImpl("KEY", "SOME_OTHER_VALUE");
        final Context context3 = new ContextImpl("OTHER_KEY", "SOME_OTHER_VALUE");

        //then:
        Assert.assertTrue(context1.equals(context2));
        Assert.assertTrue(context2.equals(context1));
        Assert.assertFalse(context1.equals(context3));
        Assert.assertFalse(context2.equals(context3));
        Assert.assertFalse(context3.equals(context1));
        Assert.assertFalse(context3.equals(context2));
    }

    @Test
    public void testHash() {
        //given:
        final Context context1 = new ContextImpl("KEY", "SOME_VALUE");
        final Context context2 = new ContextImpl("KEY", "SOME_OTHER_VALUE");
        final Context context3 = new ContextImpl("OTHER_KEY", "SOME_OTHER_VALUE");
        final HashSet<Context> set = new HashSet<>();

        //when:
        set.add(context1);
        set.add(context2);
        set.add(context3);

        //then:
        Assert.assertEquals(set.size(), 2);
    }

}
