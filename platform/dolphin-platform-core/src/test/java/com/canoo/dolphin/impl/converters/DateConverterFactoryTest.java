package com.canoo.dolphin.impl.converters;

import com.canoo.dolphin.converter.Converter;
import com.canoo.dolphin.converter.ValueConverterException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by hendrikebbers on 13.03.17.
 */
public class DateConverterFactoryTest {

    @Test
    public void testTypeIdentifier() {
        DateConverterFactory factory = new DateConverterFactory();
        Assert.assertEquals(factory.getTypeIdentifier(), DateConverterFactory.FIELD_TYPE_DATE);
    }

    @Test
    public void testTypeSupport() {
        DateConverterFactory factory = new DateConverterFactory();
        Assert.assertTrue(factory.supportsType(Date.class));
    }

    @Test
    public void testConverterCreation() {
        DateConverterFactory factory = new DateConverterFactory();
        Converter converter = factory.getConverterForType(Date.class);
        Assert.assertNotNull(converter);
    }

    @Test
    public void testConvertion() throws ValueConverterException {
        DateConverterFactory factory = new DateConverterFactory();
        Converter converter = factory.getConverterForType(Date.class);

        Date date = new Date();
        Object converted = converter.convertToDolphin(date);
        Assert.assertNotNull(converted);
        Assert.assertEquals(converter.convertFromDolphin(converted), date);
    }

}
