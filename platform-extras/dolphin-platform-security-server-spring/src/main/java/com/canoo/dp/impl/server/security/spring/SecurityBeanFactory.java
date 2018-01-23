package com.canoo.dp.impl.server.security.spring;

import com.canoo.dp.impl.server.security.DolphinSecurityBootstrap;
import com.canoo.platform.server.security.SecurityContext;
import org.apiguardian.api.API;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.19.0", status = INTERNAL)
@Configuration
public class SecurityBeanFactory {

    @Bean("security")
    @RequestScope
    public SecurityContext createDolphinSession() {
        return DolphinSecurityBootstrap.getInstance().getSecurityForCurrentRequest();
    }

    @Bean
    @ApplicationScope
    public RestTemplate restTemplate(final SecurityClientRequestFactory requestFactory) {
        final RestTemplate template = new RestTemplate(requestFactory);
        return template;
    }

    @Bean
    @ApplicationScope
    public SecurityClientRequestFactory securityClientRequestFactory() {
        return new SecurityClientRequestFactory(() -> DolphinSecurityBootstrap.getInstance().tokenForCurrentRequest().orElse(null));
    }
}
