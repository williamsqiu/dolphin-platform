package com.canoo.impl.platform.core;

import com.canoo.dp.impl.platform.core.IdentitySet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

public class IdentitySetTest {

    @Test
    public void testWithDate() {
        //given:
        final IdentitySet<Date> set = new IdentitySet<>();

        final long time = System.currentTimeMillis();
        final Date date1 = new Date(time);
        final Date date2 = new Date(time);
        final Date date3 = new Date(time + 1);

        //when:
        set.add(date1);
        set.add(date2);
        set.add(date3);

        //then:
        Assert.assertEquals(set.size(), 3);
    }

}
