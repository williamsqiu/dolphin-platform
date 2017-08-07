package com.canoo.dp.impl.server.security.javaee;

import com.canoo.dp.impl.server.security.DolphinSecurityBootstrap;
import com.canoo.platform.server.security.Security;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class SecurityBeanFactory {

    @Produces
    @RequestScoped
    public Security createDolphinSession() {
        return DolphinSecurityBootstrap.getInstance().getSecurityForCurrentRequest();
    }
}
