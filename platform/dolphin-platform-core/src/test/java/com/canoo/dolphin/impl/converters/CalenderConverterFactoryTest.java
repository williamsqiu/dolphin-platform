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
