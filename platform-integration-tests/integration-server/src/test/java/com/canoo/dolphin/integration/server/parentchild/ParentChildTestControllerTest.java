/*
 * Copyright 2015-2018 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dolphin.integration.server.parentchild;

import com.canoo.dolphin.integration.parentchild.ParentTestBean;
import com.canoo.dolphin.integration.server.TestConfiguration;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.spring.test.ControllerUnderTest;
import com.canoo.platform.spring.test.SpringTestNGControllerTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.canoo.dolphin.integration.parentchild.ParentChildTestConstants.CHILD_CONTROLLER_NAME;
import static com.canoo.dolphin.integration.parentchild.ParentChildTestConstants.PARENT_CONTROLLER_NAME;

@SpringBootTest(classes = TestConfiguration.class)
public class ParentChildTestControllerTest extends SpringTestNGControllerTest {

    private ControllerUnderTest<ParentTestBean> controller;

    @BeforeMethod
    public void init() {
        controller = createController(PARENT_CONTROLLER_NAME);
        controller.createController(CHILD_CONTROLLER_NAME);
    }

    public void destroy() {
        controller.destroy();
    }

    @Test
    public void testPostChildCreatedCalled() {

        Assert.assertTrue(controller.getModel().getPostChildCreatedCalled());
        destroy();
    }

    @Test
    public void testPreChildDestroyedCalled() {
        Property<Boolean> preDestroyCalledProperty = controller.getModel().preChildDestroyedCalledProperty();
        destroy();
        Assert.assertTrue(preDestroyCalledProperty.get());
    }

}
