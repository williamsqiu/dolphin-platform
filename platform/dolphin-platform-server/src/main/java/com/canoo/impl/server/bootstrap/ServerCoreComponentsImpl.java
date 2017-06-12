package com.canoo.impl.server.bootstrap;

import com.canoo.dolphin.concurrency.PlatformThreadFactory;
import com.canoo.dolphin.util.Assert;
import com.canoo.impl.server.beans.ManagedBeanFactory;
import com.canoo.platform.server.spi.ClasspathScanner;
import com.canoo.platform.server.spi.PlatformConfiguration;
import com.canoo.platform.server.spi.ServerCoreComponents;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

public class ServerCoreComponentsImpl implements ServerCoreComponents {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    protected ServerCoreComponentsImpl(final ServletContext servletContext, final PlatformConfiguration configuration, final PlatformThreadFactory threadFactory, ClasspathScanner classpathScanner, ManagedBeanFactory managedBeanFactory) {
        Assert.requireNonNull(servletContext, "servletContext");
        Assert.requireNonNull(configuration, "configuration");
        Assert.requireNonNull(threadFactory, "threadFactory");
        Assert.requireNonNull(classpathScanner, "classpathScanner");
        Assert.requireNonNull(managedBeanFactory, "managedBeanFactory");

        provideInstance(ServletContext.class, servletContext);
        provideInstance(PlatformConfiguration.class, configuration);
        provideInstance(PlatformThreadFactory.class, threadFactory);
        provideInstance(ClasspathScanner.class, classpathScanner);
        provideInstance(ManagedBeanFactory.class, managedBeanFactory);
    }

    public ServletContext getServletContext() {
        return getInstance(ServletContext.class);
    }

    public PlatformConfiguration getConfiguration() {
        return getInstance(PlatformConfiguration.class);
    }

    public PlatformThreadFactory getThreadFactory() {
        return getInstance(PlatformThreadFactory.class);
    }

    public ClasspathScanner getClasspathScanner() {
        return getInstance(ClasspathScanner.class);
    }

    public ManagedBeanFactory getManagedBeanFactory() {
        return getInstance(ManagedBeanFactory.class);
    }

    public <T> void provideInstance(Class<T> cls, T instance) {
        Assert.requireNonNull(cls, "cls");
        Assert.requireNonNull(instance, "instance");

        if(getInstance(cls) != null) {
            throw new IllegalStateException("Instance for class " + cls + " already provided");
        }
        instances.put(cls, instance);
    }

    public <T> T getInstance(Class<T> cls) {
        Assert.requireNonNull(cls, "cls");
        return (T) instances.get(cls);
    }
}
