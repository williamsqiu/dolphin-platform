package com.canoo.platform.server.spi;

public @interface ModuleDefinition {

    String value();

    int order() default 100;

}
