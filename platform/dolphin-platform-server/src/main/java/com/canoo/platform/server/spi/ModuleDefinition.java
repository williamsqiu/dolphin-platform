package com.canoo.platform.server.spi;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Inherited
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleDefinition {

    String value();

    int order() default 100;

}
