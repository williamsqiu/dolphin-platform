package com.canoo.platform.server.spi;

import com.canoo.dolphin.concurrency.PlatformThreadFactory;
import com.canoo.impl.server.beans.ManagedBeanFactory;

import javax.servlet.ServletContext;

public interface ServerCoreComponents {

    ServletContext getServletContext();

    PlatformConfiguration getConfiguration();

    PlatformThreadFactory getThreadFactory();

    ClasspathScanner getClasspathScanner();

    ManagedBeanFactory getManagedBeanFactory();

    <T> void provideInstance(Class<T> cls, T instance);

    <T> T getInstance(Class<T> cls);
}
