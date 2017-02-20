package com.canoo.dolphin.server.servlet;

import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class CrossSiteOriginFilterTest {

    @Test
    public void testCommaSeparatedStringWithValidList(){
        CrossSiteOriginFilter crossSiteOriginFilter = new CrossSiteOriginFilter(new DolphinPlatformConfiguration());
        String commaSeparatedList = crossSiteOriginFilter.getAsCommaSeparatedList(Arrays.asList("origin", "authorization", "accept"));
        assertEquals("origin,authorization,accept",commaSeparatedList);
    }
    @Test(expectedExceptions = NullPointerException.class)
    public void testCommaSeparatedList(){
        CrossSiteOriginFilter crossSiteOriginFilter = new CrossSiteOriginFilter(new DolphinPlatformConfiguration());
        crossSiteOriginFilter.getAsCommaSeparatedList(null);
    }
}
