package com.canoo.dolphin.integration.property;

import com.canoo.dolphin.mapping.DolphinBean;
import com.canoo.dolphin.mapping.Property;

import java.lang.annotation.ElementType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@DolphinBean
public class PropertyTestBean {

    private Property<String> stringValue;

    private Property<Boolean> booleanValue;

    private Property<Integer> integerValue;

    private Property<Long> longValue;

    private Property<Float> floatValue;

    private Property<Double> doubleValue;

    private Property<BigDecimal> bigDecimalValue;

    private Property<BigInteger> bigIntegerValue;

    private Property<Byte> byteValue;

    private Property<Calendar> calendarValue;

    private Property<Date> dateValue;

    private Property<Short> shortValue;

    private Property<UUID> uuidValue;

    private Property<ElementType> enumValue;

    public Property<String> stringValueProperty() {
        return stringValue;
    }

    public Property<Boolean> booleanValueProperty() {
        return booleanValue;
    }

    public Property<Integer> integerValueProperty() {
        return integerValue;
    }

    public Property<Long> longValueProperty() {
        return longValue;
    }

    public Property<Float> floatValueProperty() {
        return floatValue;
    }

    public Property<Double> doubleValueProperty() {
        return doubleValue;
    }

    public Property<BigDecimal> bigDecimalValueProperty() {
        return bigDecimalValue;
    }

    public Property<BigInteger> bigIntegerValueProperty() {
        return bigIntegerValue;
    }

    public Property<Byte> byteValueProperty() {
        return byteValue;
    }

    public Property<Calendar> calendarValueProperty() {
        return calendarValue;
    }

    public Property<Date> dateValueProperty() {
        return dateValue;
    }

    public Property<Short> shortValueProperty() {
        return shortValue;
    }

    public Property<UUID> uuidValueProperty() {
        return uuidValue;
    }

    public Property<ElementType> enumValueProperty() {
        return enumValue;
    }

    public String getStringValue() {
        return stringValue.get();
    }

    public Boolean getBooleanValue() {
        return booleanValue.get();
    }

    public Integer getIntegerValue() {
        return integerValue.get();
    }

    public Long getLongValue() {
        return longValue.get();
    }

    public Float getFloatValue() {
        return floatValue.get();
    }

    public Double getDoubleValue() {
        return doubleValue.get();
    }

    public BigDecimal getBigDecimalValue() {
        return bigDecimalValue.get();
    }

    public BigInteger getBigIntegerValue() {
        return bigIntegerValue.get();
    }

    public Byte getByteValue() {
        return byteValue.get();
    }

    public Calendar getCalendarValue() {
        return calendarValue.get();
    }

    public Date getDateValue() {
        return dateValue.get();
    }

    public Short getShortValue() {
        return shortValue.get();
    }

    public UUID getUuidValue() {
        return uuidValue.get();
    }

    public ElementType getEnumValue() {
        return enumValue.get();
    }

    public void setStringValue(String stringValue) {
        this.stringValue.set(stringValue);
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue.set(booleanValue);
    }

    public void setIntegerValue(Integer integerValue) {
        this.integerValue.set(integerValue);
    }

    public void setLongValue(Long longValue) {
        this.longValue.set(longValue);
    }

    public void setFloatValue(Float floatValue) {
        this.floatValue.set(floatValue);
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue.set(doubleValue);
    }

    public void setBigDecimalValue(BigDecimal bigDecimalValue) {
        this.bigDecimalValue.set(bigDecimalValue);
    }

    public void setBigIntegerValue(BigInteger bigIntegerValue) {
        this.bigIntegerValue.set(bigIntegerValue);
    }

    public void setByteValue(Byte byteValue) {
        this.byteValue.set(byteValue);
    }

    public void setCalendarValue(Calendar calendarValue) {
        this.calendarValue.set(calendarValue);
    }

    public void setDateValue(Date dateValue) {
        this.dateValue.set(dateValue);
    }

    public void setShortValue(Short shortValue) {
        this.shortValue.set(shortValue);
    }

    public void setUuidValue(UUID uuidValue) {
        this.uuidValue.set(uuidValue);
    }

    public void setEnumValue(ElementType enumValue) {
        this.enumValue.set(enumValue);
    }

}
