package com.canoo.dp.impl.platform.projector.converter;

import com.canoo.dp.impl.remoting.converters.AbstractStringConverter;
import com.canoo.platform.remoting.spi.converter.Converter;
import com.canoo.platform.remoting.spi.converter.ConverterFactory;
import com.canoo.platform.remoting.spi.converter.DolphinBeanRepo;
import com.canoo.platform.remoting.spi.converter.ValueConverterException;

import java.util.Collections;
import java.util.List;

public class ClassConverterFactory implements ConverterFactory {

    private final static ClassConverter CONVERTER = new ClassConverter();

    @Override
    public void init(final DolphinBeanRepo beanRepository) {}

    @Override
    public boolean supportsType(Class<?> cls) {
        return cls.equals(Class.class);
    }

    @Override
    public List<Class> getSupportedTypes() {
        return Collections.singletonList(Class.class);
    }

    @Override
    public int getTypeIdentifier() {
        return 100;
    }

    @Override
    public Converter getConverterForType(Class<?> cls) {
        return CONVERTER;
    }

    private static class ClassConverter extends AbstractStringConverter<Class> {

        @Override
        public Class convertFromDolphin(String value) throws ValueConverterException {
            if(value == null) {
                return null;
            }
            try {
                return Class.forName(value);
            } catch (ClassNotFoundException e) {
                throw new ValueConverterException("Can not find class " + value, e);
            }
        }

        @Override
        public String convertToDolphin(Class value) throws ValueConverterException {
            if(value == null) {
                return null;
            }
            return value.getName();
        }
    }
}
