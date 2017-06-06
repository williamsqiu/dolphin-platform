package com.canoo.dolphin.util;

public interface Function<T, R> {

    R call(T t);
}