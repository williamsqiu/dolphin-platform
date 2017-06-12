package com.canoo.platform.server.spi;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface ClasspathScanner {

    Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation);
}
