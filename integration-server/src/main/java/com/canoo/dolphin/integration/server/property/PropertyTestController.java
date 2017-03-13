package com.canoo.dolphin.integration.server.property;

import com.canoo.dolphin.integration.property.PropertyTestBean;
import com.canoo.dolphin.server.DolphinAction;
import com.canoo.dolphin.server.DolphinController;
import com.canoo.dolphin.server.DolphinModel;

import java.util.Objects;

import static com.canoo.dolphin.integration.IntegrationConstants.NOT_NULL;
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

@DolphinController(PROPERTY_CONTROLLER_NAME)
public class PropertyTestController {

    @DolphinModel
    private PropertyTestBean model;

    @DolphinAction(CHECK_MODEL_CREATION_ACTION)
    public void checkModelCreation() {
        Objects.requireNonNull(model, "Model should not be null");
        Objects.requireNonNull(model.bigDecimalValueProperty(), "big decimal property" + NOT_NULL);
        Objects.requireNonNull(model.bigIntegerValueProperty(), "big integer property" + NOT_NULL);
        Objects.requireNonNull(model.booleanValueProperty(), "boolean property" + NOT_NULL);
        Objects.requireNonNull(model.byteValueProperty(), "byte property" + NOT_NULL);
        Objects.requireNonNull(model.calendarValueProperty(), "calender property" + NOT_NULL);
        Objects.requireNonNull(model.dateValueProperty(), "date property" + NOT_NULL);
        Objects.requireNonNull(model.doubleValueProperty(), "double property" + NOT_NULL);
        Objects.requireNonNull(model.enumValueProperty(), "enum property" + NOT_NULL);
        Objects.requireNonNull(model.floatValueProperty(), "float property" + NOT_NULL);
        Objects.requireNonNull(model.integerValueProperty(), "integer property" + NOT_NULL);
        Objects.requireNonNull(model.longValueProperty(), "long property" + NOT_NULL);
        Objects.requireNonNull(model.shortValueProperty(), "short property" + NOT_NULL);
        Objects.requireNonNull(model.stringValueProperty(), "string property" + NOT_NULL);
        Objects.requireNonNull(model.uuidValueProperty(), "uuid property" + NOT_NULL);
    }

    @DolphinAction(SET_TO_DEFAULTS_ACTION)
    public void setToDefaults() {
        model.setBigDecimalValue(BIG_DECIMAL_VALUE);
        model.setBigIntegerValue(BIG_INTEGER_VALUE);
        model.setBooleanValue(BOOLEAN_VALUE);
        model.setByteValue(BYTE_VALUE);
        model.setCalendarValue(CALENDAR_VALUE);
        model.setDateValue(DATE_VALUE);
        model.setDoubleValue(DOUBLE_VALUE);
        model.setEnumValue(ENUM_VALUE);
        model.setFloatValue(FLOAT_VALUE);
        model.setIntegerValue(INTEGER_VALUE);
        model.setLongValue(LONG_VALUE);
        model.setShortValue(SHORT_VALUE);
        model.setStringValue(STRING_VALUE);
        model.setUuidValue(UUID_VALUE);
    }

    @DolphinAction(RESET_TO_NULL_ACTION)
    public void resetToNull() {
        model.setBigDecimalValue(null);
        model.setBigIntegerValue(null);
        model.setBooleanValue(null);
        model.setByteValue(null);
        model.setCalendarValue(null);
        model.setDateValue(null);
        model.setDoubleValue(null);
        model.setEnumValue(null);
        model.setFloatValue(null);
        model.setIntegerValue(null);
        model.setLongValue(null);
        model.setShortValue(null);
        model.setStringValue(null);
        model.setUuidValue(null);
    }

}
