package com.canoo.platform.server.spring;

import com.canoo.dp.impl.server.spring.DolphinPlatformSpringBootstrap;
import com.canoo.dp.impl.server.spring.SpringBeanFactory;
import org.apiguardian.api.API;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apiguardian.api.API.Status.MAINTAINED;

/**
 * Enables the Dolphin Platform in a Spring based application.
 *
 * <p>To be used together with @{@link Configuration Configuration}:
 *
 * <pre class="code">
 * &#064;Configuration
 * &#064;EnableDolphinPlatform
 * public class AppConfig {
 *
 * }</pre>
 *
 *
 * @author Hendrik Ebbers
 */
@Import({DolphinPlatformSpringBootstrap.class, SpringBeanFactory.class})
@Documented
@Target({TYPE})
@Retention(RUNTIME)
@API(since = "0.x", status = MAINTAINED)
public @interface EnableDolphinPlatform {
}
