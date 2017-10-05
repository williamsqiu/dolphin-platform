/*
 * Copyright 2015-2017 Canoo Engineering AG.
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

import com.canoo.dolphin.integration.qualifier.QualifierTestBean;
import com.canoo.dolphin.integration.qualifier.QualifierTestSubBeanOne;
import com.canoo.dolphin.integration.qualifier.QualifierTestSubBeanTwo;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ControllerProxy;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.BIND_ACTION;
import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.DUMMY_ACTION;
import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.QUALIFIER_CONTROLLER_NAME;
import static com.canoo.dolphin.integration.qualifier.QualifierTestConstants.UNBIND_ACTION;
import static org.testng.Assert.assertEquals;

public class QualifierControllerTest extends AbstractIntegrationTest {


    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test if controller and model can be created")
    public void testCreateController(String containerType, String endpoint) {
        try {
            final ClientContext context = connect(endpoint);
            final ControllerProxy<QualifierTestBean> controller = createController(context, QUALIFIER_CONTROLLER_NAME);
            Assert.assertNotNull(controller);
            Assert.assertNotNull(controller.getModel());
            assertEquals(controller.getModel().getClass(), QualifierTestBean.class);
            destroy(controller, endpoint);
            disconnect(context, endpoint);

        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test if QualifierTestSubBeanTwo is sync when updating QualifierTestSubBeanOne")
    public void testQualifier1(String containerType, String endpoint) {
        try {
            final ClientContext context = connect(endpoint);
            final ControllerProxy<QualifierTestBean> controller = createController(context, QUALIFIER_CONTROLLER_NAME);

            //given:
            final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
            final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

            //when:
            setSubBeanOneValue(subBeanOne, 42, true, "Test1");

            invoke(controller, DUMMY_ACTION, containerType);

            //then:
            assertSubBeanOneValue(subBeanOne, 42, true, "Test1");
            assertSubBeanTwoValue(subBeanTwo, 42, true, "Test1");

            //Destroy and Disconnect Controller
            destroy(controller, endpoint);
            disconnect(context, endpoint);

        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test if QualifierTestSubBeanOne is sync when updating QualifierTestSubBeanTwo")
    public void testQualifier2(String containerType, String endpoint) {
        try {

            final ClientContext context = connect(endpoint);
            final ControllerProxy<QualifierTestBean> controller = createController(context, QUALIFIER_CONTROLLER_NAME);

            //given:
            final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
            final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

            //when:
            setSubBeanTwoValue(subBeanTwo, 42, true, "Test1");

            invoke(controller, DUMMY_ACTION, containerType);

            //then:
            assertSubBeanOneValue(subBeanOne, 42, true, "Test1");
            assertSubBeanTwoValue(subBeanTwo, 42, true, "Test1");

            //Destroy and Disconnect Controller
            destroy(controller, endpoint);
            disconnect(context, endpoint);

        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test Qualifier Unbind")
    public void testQualifierUnbind(String containerType, String endpoint) {
        try {
            final ClientContext context = connect(endpoint);
            final ControllerProxy<QualifierTestBean> controller = createController(context, QUALIFIER_CONTROLLER_NAME);

            //given:
            final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
            final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

            //when:
            setSubBeanOneValue(subBeanOne, 42, true, "Test1");
            invoke(controller, UNBIND_ACTION, containerType);
            setSubBeanOneValue(subBeanOne, 44, false, "Test2");

            //then:
            assertSubBeanOneValue(subBeanOne, 44, false, "Test2");
            assertSubBeanTwoValue(subBeanTwo, 42, true, "Test1");

            //Destroy and Disconnect Controller
            destroy(controller, endpoint);
            disconnect(context, endpoint);

        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test Qualifier Not Bound")
    public void testQualifierNotBound(String containerType, String endpoint) {
        try {
            final ClientContext context = connect(endpoint);
            final ControllerProxy<QualifierTestBean> controller = createController(context, QUALIFIER_CONTROLLER_NAME);

            //given:
            final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
            final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

            //when:
            invoke(controller, UNBIND_ACTION, containerType);
            setSubBeanOneValue(subBeanOne, 42, true, "Test1");
            invoke(controller, DUMMY_ACTION, containerType);

            //then:
            assertSubBeanOneValue(subBeanOne, 42, true, "Test1");
            assertSubBeanTwoValue(subBeanTwo, null, null, null);

            //Destroy and Disconnect Controller
            destroy(controller, endpoint);
            disconnect(context, endpoint);

        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test Qualifier Rebind")
    public void testQualifierRebind(String containerType, String endpoint) {
        try {
            final ClientContext context = connect(endpoint);
            final ControllerProxy<QualifierTestBean> controller = createController(context, QUALIFIER_CONTROLLER_NAME);

            //given:
            final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
            final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

            //when:
            invoke(controller, UNBIND_ACTION, containerType);
            setSubBeanTwoValue(subBeanTwo, 42, true, "Test1");
            invoke(controller, BIND_ACTION, containerType);

            //then:
            assertSubBeanOneValue(subBeanOne, null, null, null);
            assertSubBeanTwoValue(subBeanTwo, 42, true, "Test1");

            //Destroy and Disconnect Controller
            destroy(controller, endpoint);
            disconnect(context, endpoint);

        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test Qualifier Change After Rebind")
    public void testQualifierChangeAfterRebind(String containerType, String endpoint) {
        try {
            final ClientContext context = connect(endpoint);
            final ControllerProxy<QualifierTestBean> controller = createController(context, QUALIFIER_CONTROLLER_NAME);

            //given:
            final QualifierTestSubBeanOne subBeanOne = controller.getModel().subBeanlOneProperty().get();
            final QualifierTestSubBeanTwo subBeanTwo = controller.getModel().subBeanTwoProperty().get();

            //when:
            invoke(controller, UNBIND_ACTION, containerType);
            setSubBeanOneValue(subBeanOne, 42, true, "Test1");
            invoke(controller, BIND_ACTION, containerType);
            setSubBeanTwoValue(subBeanTwo, 44, false, "Test2");

            //then:
            assertSubBeanOneValue(subBeanOne, 44, false, "Test2");
            assertSubBeanTwoValue(subBeanTwo, 44, false, "Test2");

            //Destroy and Disconnect Controller
            destroy(controller, endpoint);
            disconnect(context, endpoint);

        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    private void setSubBeanOneValue(final QualifierTestSubBeanOne subBeanOne, final int intValue, final boolean booleanValue, final String stringValue) {
        subBeanOne.booleanProperty().set(booleanValue);
        subBeanOne.stringProperty().set(stringValue);
        subBeanOne.integerProperty().set(intValue);
    }

    private void setSubBeanTwoValue(final QualifierTestSubBeanTwo subBeanTwo, final int intValue, final boolean booleanValue, final String stringValue) {
        subBeanTwo.booleanProperty().set(booleanValue);
        subBeanTwo.stringProperty().set(stringValue);
        subBeanTwo.integerProperty().set(intValue);
    }

    private void assertSubBeanOneValue(final QualifierTestSubBeanOne subBeanOne, final Integer intValue, final Boolean booleanValue, final String stringValue) {
        assertEquals(subBeanOne.booleanProperty().get(), booleanValue);
        assertEquals(subBeanOne.stringProperty().get(), stringValue);
        assertEquals(subBeanOne.integerProperty().get(), intValue);
    }

    private void assertSubBeanTwoValue(final QualifierTestSubBeanTwo subBeanTwo, final Integer intValue, final Boolean booleanValue, final String stringValue) {
        assertEquals(subBeanTwo.booleanProperty().get(), booleanValue);
        assertEquals(subBeanTwo.stringProperty().get(), stringValue);
        assertEquals(subBeanTwo.integerProperty().get(), intValue);
    }

}