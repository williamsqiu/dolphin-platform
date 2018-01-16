package com.canoo.dp.impl.platform.client.http;

@FunctionalInterface
public interface ResponseContentConverter<T> {

    T convert(byte[] rawContent) throws Exception;

}
