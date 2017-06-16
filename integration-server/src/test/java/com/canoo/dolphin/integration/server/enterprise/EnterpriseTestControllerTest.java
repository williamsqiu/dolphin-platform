package com.canoo.dolphin.integration.server.enterprise;

import com.canoo.dolphin.integration.enterprise.EnterpriseTestBean;
import com.canoo.dolphin.integration.server.TestConfiguration;
import com.canoo.dolphin.mapping.Property;
import com.canoo.platform.spring.test.ControllerUnderTest;
import com.canoo.platform.spring.test.SpringTestNGControllerTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.canoo.dolphin.integration.enterprise.EnterpriseTestConstants.ENTERPRISE_CONTROLLER_NAME;

@SpringBootTest(classes = TestConfiguration.class)
public class EnterpriseTestControllerTest extends SpringTestNGControllerTest {

    private ControllerUnderTest<EnterpriseTestBean> controller;

    @BeforeMethod
    public void init() {
        controller = createController(ENTERPRISE_CONTROLLER_NAME);
    }

    public void destroy() {
        controller.destroy();
    }

    @Test
    public void testPostConstructCalled() {
        Assert.assertTrue(controller.getModel().getPostConstructCalled());
        destroy();
    }

    @Test
    public void testPreDestroyCalled() {
        Property<Boolean> preDestroyCalledProperty = controller.getModel().preDestroyCalledProperty();
        destroy();
        Assert.assertTrue(preDestroyCalledProperty.get());
    }

}
