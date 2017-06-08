package com.canoo.impl.server.beans;

import javax.servlet.ServletContext;

public interface ManagedBeanFactory {

    void init(ServletContext servletContext);

    <T> T createDependendInstance(Class<T> cls);

    <T> T createDependendInstance(Class<T> cls, PostConstructInterceptor<T> interceptor);

    <T> void destroyDependendInstance(T instance, Class<T> cls);
}
