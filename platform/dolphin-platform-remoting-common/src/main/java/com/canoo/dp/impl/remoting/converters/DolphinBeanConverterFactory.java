/*
 * Copyright 2015-2018 Canoo Engineering AG.
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
package com.canoo.dp.impl.remoting.converters;

import com.canoo.dp.impl.remoting.DolphinUtils;
import com.canoo.platform.remoting.RemotingBean;
import com.canoo.platform.remoting.spi.converter.Converter;
import com.canoo.platform.remoting.spi.converter.ConverterFactory;
import com.canoo.platform.remoting.spi.converter.DolphinBeanRepo;
import com.canoo.platform.remoting.spi.converter.ValueConverterException;
import org.apiguardian.api.API;

import java.util.Collections;
import java.util.List;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class DolphinBeanConverterFactory implements ConverterFactory {

    public final static int FIELD_TYPE_DOLPHIN_BEAN = 0;

    private Converter<Object, String> converter;

    @Override
    public void init(final DolphinBeanRepo beanRepository) {
        this.converter = new DolphinBeanConverter(beanRepository);
    }

    @Override
    public boolean supportsType(final Class<?> cls) {
        return DolphinUtils.isDolphinBean(cls);
    }

    @Override
    public List<Class> getSupportedTypes() {
        return Collections.singletonList(RemotingBean.class);
    }

    @Override
    public int getTypeIdentifier() {
        return FIELD_TYPE_DOLPHIN_BEAN;
    }

    @Override
    public Converter getConverterForType(final Class<?> cls) {
        return converter;
    }

    private class DolphinBeanConverter extends AbstractStringConverter<Object> {

        private final DolphinBeanRepo beanRepository;

        public DolphinBeanConverter(final DolphinBeanRepo beanRepository) {
            this.beanRepository = beanRepository;
        }

        @Override
        public Object convertFromDolphin(final String value) throws ValueConverterException {
            try {
                return beanRepository.getBean(value);
            } catch (Exception e) {
                throw new ValueConverterException("Can not convert bean with id: "+ value +" to dolphin bean", e);
            }
        }

        @Override
        public String convertToDolphin(final Object value) throws ValueConverterException {
            try {
                return beanRepository.getDolphinId(value);
            } catch (Exception e) {
                throw new ValueConverterException("Can not convert from dolphin bean", e);
            }
        }
    }
}
