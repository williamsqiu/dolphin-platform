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

import com.canoo.dolphin.integration.action.ActionTestBean;
import com.canoo.platform.remoting.client.ClientContext;
import com.canoo.platform.remoting.client.ControllerProxy;
import com.canoo.platform.remoting.client.Param;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.annotation.ElementType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.canoo.dolphin.integration.action.ActionTestConstants.ACTION_CONTROLLER_NAME;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PARAM_NAME;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PARAM_NAME_1;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PARAM_NAME_2;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PARAM_NAME_3;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_BIGDECIMAL_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_BIGINTEGER_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_BYTE_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_CALENDER_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_DATE_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_DOUBLE_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_ELEMENT_TYPE_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_FLOAT_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_INTEGER_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_LONG_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_SEVERAL_BIGDECIMAL_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_SEVERAL_BIGINTEGER_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_SEVERAL_BYTE_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_SEVERAL_DOUBLE_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_SEVERAL_FLOAT_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_SEVERAL_INTEGER_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_SEVERAL_LONG_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_SEVERAL_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_SEVERAL_SHORT_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_SHORT_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_STRING_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PRIVATE_WITH_UUID_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_BIGDECIMAL_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_BIGINTEGER_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_BOOLEAN_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_BYTE_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_CALENDER_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_DATE_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_DOUBLE_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_ELEMENT_TYPE_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_FLOAT_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_INTEGER_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_LONG_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_SEVERAL_BIGDECIMAL_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_SEVERAL_BIGINTEGER_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_SEVERAL_BYTE_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_SEVERAL_DOUBLE_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_SEVERAL_FLOAT_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_SEVERAL_INTEGER_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_SEVERAL_LONG_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_SEVERAL_SHORT_PARAMS_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_SHORT_PARAM_ACTION;
import static com.canoo.dolphin.integration.action.ActionTestConstants.PUBLIC_WITH_UUID_PARAM_ACTION;

public class ActionControllerTest extends AbstractIntegrationTest {

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test if controller and model can be created")
    public void testCreateController(String containerType, String endpoint) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            Assert.assertNotNull(controller);
            Assert.assertNotNull(controller.getModel());
            Assert.assertEquals(controller.getModel().getClass(), ActionTestBean.class);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method can be called")
    public void testCallPublicMethod(String containerType, String endpoint) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, PUBLIC_ACTION, containerType);
            Assert.assertTrue(controller.getModel().getBooleanValue());
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action with param method can be called")
    public void testCallPublicMethodWithParam(String containerType, String endpoint) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, PUBLIC_WITH_BOOLEAN_PARAM_ACTION, containerType, new Param(PARAM_NAME, true));
            Assert.assertTrue(controller.getModel().getBooleanValue());
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action with null value for param method can be called")
    public void testCallPublicMethodWithNullParam(String containerType, String endpoint) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, PUBLIC_WITH_BOOLEAN_PARAM_ACTION, containerType, new Param(PARAM_NAME, null));
            Assert.assertNull(controller.getModel().getBooleanValue());
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method can be called")
    public void testCallPrivateMethod(String containerType, String endpoint) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, PRIVATE_ACTION, containerType);
            Assert.assertTrue(controller.getModel().getBooleanValue());
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with one param can be called")
    public void testCallWithParam(String containerType, String endpoint) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, PRIVATE_WITH_STRING_PARAM_ACTION, containerType, new Param(PARAM_NAME, "Yeah!"));
            Assert.assertTrue(controller.getModel().getBooleanValue());
            Assert.assertEquals(controller.getModel().getStringValue(), "Yeah!");
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with null value for param can be called")
    public void testCallWithNullParam(String containerType, String endpoint) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, PRIVATE_WITH_STRING_PARAM_ACTION, containerType, new Param(PARAM_NAME, null));
            Assert.assertTrue(controller.getModel().getBooleanValue());
            Assert.assertEquals(controller.getModel().getStringValue(), null);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method can be called")
    public void testCallWithParams(String containerType, String endpoint) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);

            String value1 = "Hello Dolphin Platform!";
            String value2 = "I want to test you!";
            int value3 = 356;

            invoke(controller, PRIVATE_WITH_SEVERAL_PARAMS_ACTION, containerType, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2), new Param(PARAM_NAME_3, value3));
            Assert.assertTrue(controller.getModel().getBooleanValue());
            Assert.assertEquals(controller.getModel().getStringValue(), value1 + value2 + value3);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** Start Integer Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with Integer param can be called")
    public void testCallPublicMethodWithIntegerParams(String containerType, String endpoint) {
        int value = 10;
        performActionForInteger(containerType, endpoint, PUBLIC_WITH_INTEGER_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with Integer param can be called")
    public void testCallPrivateMethodWithIntegerParams(String containerType, String endpoint) {
        int value = 10;
        performActionForInteger(containerType, endpoint, PRIVATE_WITH_INTEGER_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with several Integer param can be called")
    public void callPublicMethodWithSeveralIntegerParams(String containerType, String endpoint) {
        int value1 = 1, value2 = 2, value3 = 3;
        int result = value1 + value2 + value3;
        performActionForInteger(containerType, endpoint, PUBLIC_WITH_SEVERAL_INTEGER_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2), new Param(PARAM_NAME_3, value3));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with several Integer param can be called")
    public void callPrivateMethodWithSeveralIntegerParams(String containerType, String endpoint) {
        int value1 = 1, value2 = 2;
        int result = value1 + value2;
        performActionForInteger(containerType, endpoint, PRIVATE_WITH_SEVERAL_INTEGER_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2));
    }

    private void performActionForInteger(String containerType, String endpoint, String action, int result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getIntegerValue().intValue(), result);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End Integer Type Related Integration Test */


    /** Start Long Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with Long param can be called")
    public void testCallPublicMethodWithLongParams(String containerType, String endpoint) {
        long value = 10L;
        performActionForLong(containerType, endpoint, PUBLIC_WITH_LONG_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with Long param can be called")
    public void testCallPrivateMethodWithLongParams(String containerType, String endpoint) {
        long value = 10L;
        performActionForLong(containerType, endpoint, PRIVATE_WITH_LONG_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with several Long param can be called")
    public void callPublicMethodWithSeveralLongParams(String containerType, String endpoint) {
        long value1 = 1L, value2 = 2L, value3 = 3L;
        long result = value1 + value2 + value3;
        performActionForLong(containerType, endpoint, PUBLIC_WITH_SEVERAL_LONG_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2), new Param(PARAM_NAME_3, value3));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with several Long param can be called")
    public void callPrivateMethodWithSeveralLongParams(String containerType, String endpoint) {
        long value1 = 1L, value2 = 2L;
        long result = value1 + value2;
        performActionForLong(containerType, endpoint, PRIVATE_WITH_SEVERAL_LONG_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2));
    }

    private void performActionForLong(String containerType, String endpoint, String action, long result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getLongValue().longValue(), result);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End Long Type Related Integration Test */


    /** Start Float Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with Float param can be called")
    public void testCallPublicMethodWithFloatParams(String containerType, String endpoint) {
        float value = 10.0F;
        performActionForFloat(containerType, endpoint, PUBLIC_WITH_FLOAT_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with Float param can be called")
    public void testCallPrivateMethodWithFloatParams(String containerType, String endpoint) {
        float value = 10.0F;
        performActionForFloat(containerType, endpoint, PRIVATE_WITH_FLOAT_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with several Float param can be called")
    public void callPublicMethodWithSeveralFloatParams(String containerType, String endpoint) {
        float value1 = 1.0F, value2 = 2.0F, value3 = 3.0F;
        float result = value1 + value2 + value3;
        performActionForFloat(containerType, endpoint, PUBLIC_WITH_SEVERAL_FLOAT_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2), new Param(PARAM_NAME_3, value3));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with several Float param can be called")
    public void callPrivateMethodWithSeveralFloatParams(String containerType, String endpoint) {
        float value1 = 1.0F, value2 = 2.0F;
        float result = value1 + value2;
        performActionForFloat(containerType, endpoint, PRIVATE_WITH_SEVERAL_FLOAT_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2));
    }

    private void performActionForFloat(String containerType, String endpoint, String action, float result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getFloatValue().floatValue(), result);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End Float Type Related Integration Test */


    /** Start Double Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with Double param can be called")
    public void testCallPublicMethodWithDoubleParams(String containerType, String endpoint) {
        double value = 10.0;
        performActionForDouble(containerType, endpoint, PUBLIC_WITH_DOUBLE_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with Double param can be called")
    public void testCallPrivateMethodWithDoubleParams(String containerType, String endpoint) {
        double value = 10.0;
        performActionForDouble(containerType, endpoint, PRIVATE_WITH_DOUBLE_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with several Double param can be called")
    public void callPublicMethodWithSeveralDoubleParams(String containerType, String endpoint) {
        double value1 = 1.0, value2 = 2.0, value3 = 3.0;
        double result = value1 + value2 + value3;
        performActionForDouble(containerType, endpoint, PUBLIC_WITH_SEVERAL_DOUBLE_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2), new Param(PARAM_NAME_3, value3));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with several Double param can be called")
    public void callPrivateMethodWithSeveralDoubleParams(String containerType, String endpoint) {
        double value1 = 1.0, value2 = 2.0;
        double result = value1 + value2;
        performActionForDouble(containerType, endpoint, PRIVATE_WITH_SEVERAL_DOUBLE_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2));
    }

    private void performActionForDouble(String containerType, String endpoint, String action, double result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getDoubleValue().doubleValue(), result);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End Double Type Related Integration Test */


    /** Start BigDecimal Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with BigDecimal param can be called")
    public void testCallPublicMethodWithBigDecimalParams(String containerType, String endpoint) {
        BigDecimal value = BigDecimal.TEN;
        performActionForBigDecimal(containerType, endpoint, PUBLIC_WITH_BIGDECIMAL_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with BigDecimal param can be called")
    public void testCallPrivateMethodWithBigDecimalParams(String containerType, String endpoint) {
        BigDecimal value = BigDecimal.TEN;
        performActionForBigDecimal(containerType, endpoint, PRIVATE_WITH_BIGDECIMAL_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with several BigDecimal param can be called")
    public void callPublicMethodWithSeveralBigDecimalParams(String containerType, String endpoint) {
        BigDecimal value1 = BigDecimal.TEN, value2 = BigDecimal.TEN, value3 = BigDecimal.TEN;
        BigDecimal result = value1.add(value2).add(value3);
        performActionForBigDecimal(containerType, endpoint, PUBLIC_WITH_SEVERAL_BIGDECIMAL_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2), new Param(PARAM_NAME_3, value3));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with several BigDecimal param can be called")
    public void callPrivateMethodWithSeveralBigDecimalParams(String containerType, String endpoint) {
        BigDecimal value1 = BigDecimal.TEN, value2 = BigDecimal.TEN;
        BigDecimal result = value1.add(value2);
        performActionForBigDecimal(containerType, endpoint, PRIVATE_WITH_SEVERAL_BIGDECIMAL_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2));
    }

    private void performActionForBigDecimal(String containerType, String endpoint, String action, BigDecimal result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getBigDecimalValue(), result);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End BigDecimal Type Related Integration Test */


    /** Start BigInteger Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with BigInteger param can be called")
    public void testCallPublicMethodWithBigIntegerParams(String containerType, String endpoint) {
        BigInteger value = BigInteger.TEN;
        performActionForBigInteger(containerType, endpoint, PUBLIC_WITH_BIGINTEGER_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with BigInteger param can be called")
    public void testCallPrivateMethodWithBigIntegerParams(String containerType, String endpoint) {
        BigInteger value = BigInteger.TEN;
        performActionForBigInteger(containerType, endpoint, PRIVATE_WITH_BIGINTEGER_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with several BigInteger param can be called")
    public void callPublicMethodWithSeveralBigIntegerParams(String containerType, String endpoint) {
        BigInteger value1 = BigInteger.TEN, value2 = BigInteger.TEN, value3 = BigInteger.TEN;
        BigInteger result = value1.add(value2).add(value3);
        performActionForBigInteger(containerType, endpoint, PUBLIC_WITH_SEVERAL_BIGINTEGER_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2), new Param(PARAM_NAME_3, value3));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with several BigInteger param can be called")
    public void callPrivateMethodWithSeveralBigIntegerParams(String containerType, String endpoint) {
        BigInteger value1 = BigInteger.TEN, value2 = BigInteger.TEN;
        BigInteger result = value1.add(value2);
        performActionForBigInteger(containerType, endpoint, PRIVATE_WITH_SEVERAL_BIGINTEGER_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2));
    }

    private void performActionForBigInteger(String containerType, String endpoint, String action, BigInteger result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getBigIntegerValue(), result);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End BigInteger Type Related Integration Test */


    /** Start Byte Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with Byte param can be called")
    public void testCallPublicMethodWithByteParams(String containerType, String endpoint) {
        byte value = 10;
        performActionForByte(containerType, endpoint, PUBLIC_WITH_BYTE_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with Byte param can be called")
    public void testCallPrivateMethodWithByteParams(String containerType, String endpoint) {
        byte value = 10;
        performActionForByte(containerType, endpoint, PRIVATE_WITH_BYTE_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with several Byte param can be called")
    public void callPublicMethodWithSeveralByteParams(String containerType, String endpoint) {
        byte value1 = 1, value2 = 2, value3 = 3;
        byte result = (byte)(value1 + value2 + value3);
        performActionForByte(containerType, endpoint, PUBLIC_WITH_SEVERAL_BYTE_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2), new Param(PARAM_NAME_3, value3));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with several Byte param can be called")
    public void callPrivateMethodWithSeveralByteParams(String containerType, String endpoint) {
        byte value1 = 1, value2 = 2;
        byte result = (byte)(value1 + value2);
        performActionForByte(containerType, endpoint, PRIVATE_WITH_SEVERAL_BYTE_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2));
    }

    private void performActionForByte(String containerType, String endpoint, String action, byte result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getByteValue().byteValue(), result);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End Byte Type Related Integration Test */


    /** Start Calendar Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with Calendar param can be called")
    public void testCallPublicMethodWithCalendarParams(String containerType, String endpoint) {
        Calendar value = Calendar.getInstance();
        performActionForCalendar(containerType, endpoint, PUBLIC_WITH_CALENDER_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with Calendar param can be called")
    public void testCallPrivateMethodWithCalendarParams(String containerType, String endpoint) {
        Calendar value = Calendar.getInstance();
        performActionForCalendar(containerType, endpoint, PRIVATE_WITH_CALENDER_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    private void performActionForCalendar(String containerType, String endpoint, String action, Calendar result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getCalendarValue().getTime(), result.getTime());
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End Calendar Type Related Integration Test */


    /** Start Date Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with Date param can be called")
    public void testCallPublicMethodWithDateParams(String containerType, String endpoint) {
        Date value = new Date();
        performActionForDate(containerType, endpoint, PUBLIC_WITH_DATE_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with Date param can be called")
    public void testCallPrivateMethodWithDateParams(String containerType, String endpoint) {
        Date value = new Date();
        performActionForDate(containerType, endpoint, PRIVATE_WITH_DATE_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    private void performActionForDate(String containerType, String endpoint, String action, Date result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getDateValue(), result);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End Date Type Related Integration Test */


    /** Start Short Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with Short param can be called")
    public void testCallPublicMethodWithShortParams(String containerType, String endpoint) {
        short value = 10;
        performActionForShort(containerType, endpoint, PUBLIC_WITH_SHORT_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with Short param can be called")
    public void testCallPrivateMethodWithShortParams(String containerType, String endpoint) {
        short value = 10;
        performActionForShort(containerType, endpoint, PRIVATE_WITH_SHORT_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with several Short param can be called")
    public void callPublicMethodWithSeveralShortParams(String containerType, String endpoint) {
        short value1 = 1, value2 = 2, value3 = 3;
        short result = (short) (value1 + value2 + value3);
        performActionForShort(containerType, endpoint, PUBLIC_WITH_SEVERAL_SHORT_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2), new Param(PARAM_NAME_3, value3));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with several Short param can be called")
    public void callPrivateMethodWithSeveralShortParams(String containerType, String endpoint) {
        short value1 = 1, value2 = 2;
        short result = (short) (value1 + value2);
        performActionForShort(containerType, endpoint, PRIVATE_WITH_SEVERAL_SHORT_PARAMS_ACTION, result, new Param(PARAM_NAME_1, value1), new Param(PARAM_NAME_2, value2));
    }

    private void performActionForShort(String containerType, String endpoint, String action, short result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getShortValue().shortValue(), result);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End Short Type Related Integration Test */

    /** Start UUID Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with UUID param can be called")
    public void testCallPublicMethodWithUUIDParams(String containerType, String endpoint) {
        UUID value = UUID.randomUUID();
        performActionForUUID(containerType, endpoint, PUBLIC_WITH_UUID_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with UUID param can be called")
    public void testCallPrivateMethodWithUUIDParams(String containerType, String endpoint) {
        UUID value = UUID.randomUUID();
        performActionForUUID(containerType, endpoint, PRIVATE_WITH_UUID_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    private void performActionForUUID(String containerType, String endpoint, String action, UUID result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getUuidValue(), result);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End UUID Type Related Integration Test */


    /** Start ElementType Type Related Integration Test */

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an public action method with ElementType param can be called")
    public void testCallPublicMethodWithElementTypeParams(String containerType, String endpoint) {
        ElementType value = ElementType.PARAMETER;
        performActionForElementType(containerType, endpoint, PUBLIC_WITH_ELEMENT_TYPE_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Tests if an private action method with ElementType param can be called")
    public void testCallPrivateMethodWithElementTypeParams(String containerType, String endpoint) {
        ElementType value = ElementType.METHOD;
        performActionForElementType(containerType, endpoint, PRIVATE_WITH_ELEMENT_TYPE_PARAM_ACTION, value, new Param(PARAM_NAME, value));
    }

    private void performActionForElementType(String containerType, String endpoint, String action, ElementType result, Param ... params) {
        try {
            ClientContext context = connect(endpoint);
            ControllerProxy<ActionTestBean> controller = createController(context, ACTION_CONTROLLER_NAME);
            invoke(controller, action, containerType, params);
            Assert.assertEquals(controller.getModel().getEnumValue(), result);
            destroy(controller, endpoint);
            disconnect(context, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    /** End ElementType Type Related Integration Test */

}
