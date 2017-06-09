package com.canoo.impl.server.beans;

public interface PostConstructInterceptor<T> {

    void intercept(T instance);

}
