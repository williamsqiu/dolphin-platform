package com.canoo.dolphin.integration;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.ControllerProxy;
import com.canoo.dolphin.integration.property.PropertyTestBean;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.canoo.dolphin.integration.property.PropertyTestConstants.*;

public class PropertyControllerTest extends AbstractIntegrationTest {

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test if controller and model can be created")
    public void testCreateController(String containerType, String endpoint) {
        try {
            ClientContext context = createClientContext(endpoint);
            ControllerProxy<PropertyTestBean> controller = createController(context, PROPERTY_CONTROLLER_NAME);

            Assert.assertNotNull(controller);
            Assert.assertNotNull(controller.getModel());
            Assert.assertEquals(controller.getModel().getClass(), PropertyTestBean.class);

            destroy(controller, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test if all property instances are created by default")
    public void testPropertyCreating(String containerType, String endpoint) {
        try {
            ClientContext context = createClientContext(endpoint);
            ControllerProxy<PropertyTestBean> controller = createController(context, PROPERTY_CONTROLLER_NAME);

            Assert.assertNotNull(controller.getModel().uuidValueProperty());
            Assert.assertNotNull(controller.getModel().stringValueProperty());
            Assert.assertNotNull(controller.getModel().shortValueProperty());
            Assert.assertNotNull(controller.getModel().longValueProperty());
            Assert.assertNotNull(controller.getModel().integerValueProperty());
            Assert.assertNotNull(controller.getModel().bigDecimalValueProperty());
            Assert.assertNotNull(controller.getModel().bigIntegerValueProperty());
            Assert.assertNotNull(controller.getModel().booleanValueProperty());
            Assert.assertNotNull(controller.getModel().byteValueProperty());
            Assert.assertNotNull(controller.getModel().calendarValueProperty());
            Assert.assertNotNull(controller.getModel().dateValueProperty());
            Assert.assertNotNull(controller.getModel().doubleValueProperty());
            Assert.assertNotNull(controller.getModel().enumValueProperty());
            Assert.assertNotNull(controller.getModel().floatValueProperty());

            destroy(controller, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test if all property have an null value by default")
    public void testPropertyNullValueCreating(String containerType, String endpoint) {
        try {
            ClientContext context = createClientContext(endpoint);
            ControllerProxy<PropertyTestBean> controller = createController(context, PROPERTY_CONTROLLER_NAME);

            Assert.assertNull(controller.getModel().getUuidValue());
            Assert.assertNull(controller.getModel().getStringValue());
            Assert.assertNull(controller.getModel().getShortValue());
            Assert.assertNull(controller.getModel().getLongValue());
            Assert.assertNull(controller.getModel().getIntegerValue());
            Assert.assertNull(controller.getModel().getBigDecimalValue());
            Assert.assertNull(controller.getModel().getBigIntegerValue());
            Assert.assertNull(controller.getModel().getBooleanValue());
            Assert.assertNull(controller.getModel().getByteValue());
            Assert.assertNull(controller.getModel().getCalendarValue());
            Assert.assertNull(controller.getModel().getDateValue());
            Assert.assertNull(controller.getModel().getDoubleValue());
            Assert.assertNull(controller.getModel().getEnumValue());
            Assert.assertNull(controller.getModel().getFloatValue());

            destroy(controller, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }

    @Test(dataProvider = ENDPOINTS_DATAPROVIDER, description = "Test if all property values are snychronized")
    public void testPropertyValueSet(String containerType, String endpoint) {
        try {
            ClientContext context = createClientContext(endpoint);
            ControllerProxy<PropertyTestBean> controller = createController(context, PROPERTY_CONTROLLER_NAME);
            invoke(controller, SET_TO_DEFAULTS_ACTION, containerType);

            Assert.assertEquals(controller.getModel().getBigDecimalValue(), BIG_DECIMAL_VALUE);
            Assert.assertEquals(controller.getModel().getBigIntegerValue(), BIG_INTEGER_VALUE);
            Assert.assertEquals(controller.getModel().getBooleanValue(), BOOLEAN_VALUE);
            Assert.assertEquals(controller.getModel().getByteValue(), BYTE_VALUE);


            System.out.println(controller.getModel().getCalendarValue().getTimeInMillis());
            System.out.println(CALENDAR_VALUE.getTimeInMillis());

            Assert.assertEquals(controller.getModel().getCalendarValue().getTimeInMillis(), CALENDAR_VALUE.getTimeInMillis());



            Assert.assertEquals(controller.getModel().getDateValue(), DATE_VALUE);
            Assert.assertEquals(controller.getModel().getDoubleValue(), DOUBLE_VALUE);
            Assert.assertEquals(controller.getModel().getEnumValue(), ENUM_VALUE);
            Assert.assertEquals(controller.getModel().getFloatValue(), FLOAT_VALUE);
            Assert.assertEquals(controller.getModel().getIntegerValue(), INTEGER_VALUE);
            Assert.assertEquals(controller.getModel().getLongValue(), LONG_VALUE);
            Assert.assertEquals(controller.getModel().getShortValue(), SHORT_VALUE);
            Assert.assertEquals(controller.getModel().getStringValue(), STRING_VALUE);
            Assert.assertEquals(controller.getModel().getUuidValue(), UUID_VALUE);

            destroy(controller, endpoint);
        } catch (Exception e) {
            Assert.fail("Can not create controller for " + containerType, e);
        }
    }
}
