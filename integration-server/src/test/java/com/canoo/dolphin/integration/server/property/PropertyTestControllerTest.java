package com.canoo.dolphin.integration.server.property;


import com.canoo.dolphin.integration.property.PropertyTestBean;
import com.canoo.dolphin.integration.server.TestConfiguration;
import com.canoo.dolphin.test.ControllerUnderTest;
import com.canoo.dolphin.test.SpringTestNGControllerTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.canoo.dolphin.integration.property.PropertyTestConstants.BIG_DECIMAL_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.BIG_INTEGER_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.BOOLEAN_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.BYTE_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.CALENDAR_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.CHECK_MODEL_CREATION_ACTION;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.DATE_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.DOUBLE_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.ENUM_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.FLOAT_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.INTEGER_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.LONG_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.PROPERTY_CONTROLLER_NAME;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.RESET_TO_NULL_ACTION;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.SET_TO_DEFAULTS_ACTION;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.SHORT_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.STRING_VALUE;
import static com.canoo.dolphin.integration.property.PropertyTestConstants.UUID_VALUE;

@SpringApplicationConfiguration(classes = TestConfiguration.class)
public class PropertyTestControllerTest extends SpringTestNGControllerTest {

    private ControllerUnderTest<PropertyTestBean> controller;

    @BeforeMethod
    public void init() {
        controller = createController(PROPERTY_CONTROLLER_NAME);
    }

    @AfterMethod
    public void destroy() {
        controller.destroy();
    }

    @Test
    public void testControllerCreation() {
        Assert.assertNotNull(controller);
    }

    @Test
    public void testModelCreation() {
        Assert.assertNotNull(controller.getModel());
        Assert.assertNotNull(controller.getModel().bigDecimalValueProperty());
        Assert.assertNotNull(controller.getModel().bigIntegerValueProperty());
        Assert.assertNotNull(controller.getModel().booleanValueProperty());
        Assert.assertNotNull(controller.getModel().byteValueProperty());
        Assert.assertNotNull(controller.getModel().calendarValueProperty());
        Assert.assertNotNull(controller.getModel().dateValueProperty());
        Assert.assertNotNull(controller.getModel().doubleValueProperty());
        Assert.assertNotNull(controller.getModel().enumValueProperty());
        Assert.assertNotNull(controller.getModel().floatValueProperty());
        Assert.assertNotNull(controller.getModel().integerValueProperty());
        Assert.assertNotNull(controller.getModel().longValueProperty());
        Assert.assertNotNull(controller.getModel().shortValueProperty());
        Assert.assertNotNull(controller.getModel().stringValueProperty());
        Assert.assertNotNull(controller.getModel().uuidValueProperty());
    }

    @Test
    public void testModelIsEmptyAfterCreation() {
        Assert.assertNull(controller.getModel().getBigDecimalValue());
        Assert.assertNull(controller.getModel().getBigIntegerValue());
        Assert.assertNull(controller.getModel().getBooleanValue());
        Assert.assertNull(controller.getModel().getByteValue());
        Assert.assertNull(controller.getModel().getCalendarValue());
        Assert.assertNull(controller.getModel().getDateValue());
        Assert.assertNull(controller.getModel().getDoubleValue());
        Assert.assertNull(controller.getModel().getEnumValue());
        Assert.assertNull(controller.getModel().getFloatValue());
        Assert.assertNull(controller.getModel().getIntegerValue());
        Assert.assertNull(controller.getModel().getLongValue());
        Assert.assertNull(controller.getModel().getShortValue());
        Assert.assertNull(controller.getModel().getStringValue());
        Assert.assertNull(controller.getModel().getUuidValue());

        controller.invoke(CHECK_MODEL_CREATION_ACTION);
    }

    @Test
    public void testModelNewValues() {
        controller.invoke(SET_TO_DEFAULTS_ACTION);

        Assert.assertEquals(controller.getModel().getBigDecimalValue(), BIG_DECIMAL_VALUE);
        Assert.assertEquals(controller.getModel().getBigIntegerValue(), BIG_INTEGER_VALUE);
        Assert.assertEquals(controller.getModel().getBooleanValue(), BOOLEAN_VALUE);
        Assert.assertEquals(controller.getModel().getByteValue(), BYTE_VALUE);
        Assert.assertEquals(controller.getModel().getCalendarValue().getTime(), CALENDAR_VALUE.getTime());
        Assert.assertEquals(controller.getModel().getDateValue(), DATE_VALUE);
        Assert.assertEquals(controller.getModel().getDoubleValue(), DOUBLE_VALUE);
        Assert.assertEquals(controller.getModel().getEnumValue(), ENUM_VALUE);
        Assert.assertEquals(controller.getModel().getFloatValue(), FLOAT_VALUE);
        Assert.assertEquals(controller.getModel().getIntegerValue(), INTEGER_VALUE);
        Assert.assertEquals(controller.getModel().getLongValue(), LONG_VALUE);
        Assert.assertEquals(controller.getModel().getShortValue(), SHORT_VALUE);
        Assert.assertEquals(controller.getModel().getStringValue(), STRING_VALUE);
        Assert.assertEquals(controller.getModel().getUuidValue(), UUID_VALUE);
    }

    @Test
    public void testValueChangeInModel() {
        controller.invoke(SET_TO_DEFAULTS_ACTION);

        Assert.assertEquals(controller.getModel().getBigDecimalValue(), BIG_DECIMAL_VALUE);
        Assert.assertEquals(controller.getModel().getBigIntegerValue(), BIG_INTEGER_VALUE);
        Assert.assertEquals(controller.getModel().getBooleanValue(), BOOLEAN_VALUE);
        Assert.assertEquals(controller.getModel().getByteValue(), BYTE_VALUE);
        Assert.assertEquals(controller.getModel().getCalendarValue().getTime(), CALENDAR_VALUE.getTime());
        Assert.assertEquals(controller.getModel().getDateValue(), DATE_VALUE);
        Assert.assertEquals(controller.getModel().getDoubleValue(), DOUBLE_VALUE);
        Assert.assertEquals(controller.getModel().getEnumValue(), ENUM_VALUE);
        Assert.assertEquals(controller.getModel().getFloatValue(), FLOAT_VALUE);
        Assert.assertEquals(controller.getModel().getIntegerValue(), INTEGER_VALUE);
        Assert.assertEquals(controller.getModel().getLongValue(), LONG_VALUE);
        Assert.assertEquals(controller.getModel().getShortValue(), SHORT_VALUE);
        Assert.assertEquals(controller.getModel().getStringValue(), STRING_VALUE);
        Assert.assertEquals(controller.getModel().getUuidValue(), UUID_VALUE);

        controller.invoke(RESET_TO_NULL_ACTION);

        Assert.assertNull(controller.getModel().getBigDecimalValue());
        Assert.assertNull(controller.getModel().getBigIntegerValue());
        Assert.assertNull(controller.getModel().getBooleanValue());
        Assert.assertNull(controller.getModel().getByteValue());
        Assert.assertNull(controller.getModel().getCalendarValue());
        Assert.assertNull(controller.getModel().getDateValue());
        Assert.assertNull(controller.getModel().getDoubleValue());
        Assert.assertNull(controller.getModel().getEnumValue());
        Assert.assertNull(controller.getModel().getFloatValue());
        Assert.assertNull(controller.getModel().getIntegerValue());
        Assert.assertNull(controller.getModel().getLongValue());
        Assert.assertNull(controller.getModel().getShortValue());
        Assert.assertNull(controller.getModel().getStringValue());
        Assert.assertNull(controller.getModel().getUuidValue());

        controller.invoke(SET_TO_DEFAULTS_ACTION);

        Assert.assertEquals(controller.getModel().getBigDecimalValue(), BIG_DECIMAL_VALUE);
        Assert.assertEquals(controller.getModel().getBigIntegerValue(), BIG_INTEGER_VALUE);
        Assert.assertEquals(controller.getModel().getBooleanValue(), BOOLEAN_VALUE);
        Assert.assertEquals(controller.getModel().getByteValue(), BYTE_VALUE);
        Assert.assertEquals(controller.getModel().getCalendarValue().getTime(), CALENDAR_VALUE.getTime());
        Assert.assertEquals(controller.getModel().getDateValue(), DATE_VALUE);
        Assert.assertEquals(controller.getModel().getDoubleValue(), DOUBLE_VALUE);
        Assert.assertEquals(controller.getModel().getEnumValue(), ENUM_VALUE);
        Assert.assertEquals(controller.getModel().getFloatValue(), FLOAT_VALUE);
        Assert.assertEquals(controller.getModel().getIntegerValue(), INTEGER_VALUE);
        Assert.assertEquals(controller.getModel().getLongValue(), LONG_VALUE);
        Assert.assertEquals(controller.getModel().getShortValue(), SHORT_VALUE);
        Assert.assertEquals(controller.getModel().getStringValue(), STRING_VALUE);
        Assert.assertEquals(controller.getModel().getUuidValue(), UUID_VALUE);
    }
}
