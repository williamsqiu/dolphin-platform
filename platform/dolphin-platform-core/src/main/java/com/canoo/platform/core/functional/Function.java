package com.canoo.platform.core.functional;

public interface Function<T, R> {

    R call(T t);
}