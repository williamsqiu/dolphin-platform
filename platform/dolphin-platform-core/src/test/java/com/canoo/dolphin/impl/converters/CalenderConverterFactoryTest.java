package com.canoo.dolphin.impl.converters;

import com.canoo.dolphin.converter.Converter;
import com.canoo.dolphin.converter.ValueConverterException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalenderConverterFactoryTest {

    @Test
    public void testTypeIdentifier() {
        CalendarConverterFactory factory = new CalendarConverterFactory();
        Assert.assertEquals(factory.getTypeIdentifier(), CalendarConverterFactory.FIELD_TYPE_CALENDAR);
    }

    @Test
    public void testTypeSupport() {
        CalendarConverterFactory factory = new CalendarConverterFactory();
        Assert.assertTrue(factory.supportsType(Calendar.class));
        Assert.assertTrue(factory.supportsType(GregorianCalendar.class));
    }

    @Test
    public void testConverterCreation() {
        CalendarConverterFactory factory = new CalendarConverterFactory();
        Converter converter = factory.getConverterForType(Calendar.class);
        Assert.assertNotNull(converter);

        converter = factory.getConverterForType(GregorianCalendar.class);
        Assert.assertNotNull(converter);
    }

    @Test
    public void testConversionCurrentDate() throws ValueConverterException {
        CalendarConverterFactory factory = new CalendarConverterFactory();
        Converter converter = factory.getConverterForType(Calendar.class);

        Calendar calendar = GregorianCalendar.getInstance();
        Object converted = converter.convertToDolphin(calendar);
        Assert.assertNotNull(converted);
        Assert.assertEquals(((Calendar)converter.convertFromDolphin(converted)).getTime(), calendar.getTime());
    }

    @Test
    public void testConversionFixDate() throws ValueConverterException {
        CalendarConverterFactory factory = new CalendarConverterFactory();
        Converter converter = factory.getConverterForType(Calendar.class);

        Calendar calendar = new GregorianCalendar(2017, 2, 3,4, 5, 6);
        Object converted = converter.convertToDolphin(calendar);
        Assert.assertNotNull(converted);
        Assert.assertEquals(((Calendar)converter.convertFromDolphin(converted)).getTime(), calendar.getTime());
    }

}
