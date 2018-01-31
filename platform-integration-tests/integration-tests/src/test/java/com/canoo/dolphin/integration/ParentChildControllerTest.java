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
package com.canoo.dolphin.integration;

import com.canoo.dolphin.integration.enterprise.EnterpriseTestBean;
import com.canoo.dolphin.integration.parentchild.ParentTestBean;
import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ControllerProxy;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.canoo.dolphin.integration.parentchild.ParentChildTestConstants.PARENT_CONTROLLER_NAME;


public class ParentChildControllerTest extends AbstractIntegrationTest {

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test if controller and model can be created")
    public void testCreateController(String containerType, String endpoint) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<EnterpriseTestBean> controller = createController(context, PARENT_CONTROLLER_NAME);
            Assert.assertNotNull(controller);
            Assert.assertNotNull(controller.getModel());
            Assert.assertEquals(controller.getModel().getClass(), ParentTestBean.class);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test if @PostChildCreated is called in controller")
    public void testPostConstruct(String containerType, String endpoint) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ParentTestBean> controller = createController(context, PARENT_CONTROLLER_NAME);
            Assert.assertTrue(controller.getModel().getPostChildCreatedCalled());
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test if @PreChildDestroyed is called in controller")
    public void testPreDestroy(String containerType, String endpoint) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ParentTestBean> controller = createController(context, PARENT_CONTROLLER_NAME);
            Property<Boolean> preDestroyProperty = controller.getModel().preChildDestroyedCalledProperty();
            Assert.assertNull(preDestroyProperty.get());
            destroy(controller, endpoint);
            Assert.assertTrue(preDestroyProperty.get());
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }
}
