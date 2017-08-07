package com.canoo.platform.server.spring;

import com.canoo.dp.impl.server.spring.SpringBeanFactory;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Import({SpringBeanFactory.class})
@Documented
@Target({TYPE})
@Retention(RUNTIME)
public @interface EnableSecurity {
}
