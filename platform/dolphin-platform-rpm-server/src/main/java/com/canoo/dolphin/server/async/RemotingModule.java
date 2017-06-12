package com.canoo.dolphin.server.async;

import com.canoo.dolphin.server.config.RemotingConfiguration;
import com.canoo.dolphin.util.Assert;
import com.canoo.impl.server.bootstrap.ServerCoreComponents;
import com.canoo.impl.server.bootstrap.modules.ClientSessionModule;
import com.canoo.impl.server.config.PlatformConfiguration;
import com.canoo.platform.server.spi.ModuleDefinition;
import com.canoo.platform.server.spi.ModuleInitializationException;
import com.canoo.platform.server.spi.ServerModule;
import org.atmosphere.cpr.SessionSupport;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Collections;
import java.util.List;

@ModuleDefinition(value = "RemotingModule", order = 102)
public class RemotingModule implements ServerModule {

    @Override
    public List<String> getModuleDependencies() {
        return Collections.singletonList(ClientSessionModule.CLIENT_SESSION_MODULE);
    }

    @Override
    public boolean shouldBoot(PlatformConfiguration configuration) {
        final RemotingConfiguration remotingConfiguration = new RemotingConfiguration(configuration);
        return remotingConfiguration.isRemotingActive();
    }

    @Override
    public void initialize(final ServerCoreComponents coreComponents) throws ModuleInitializationException {
        Assert.requireNonNull(coreComponents, "coreComponents");
        installServlet(coreComponents.getServletContext());
        installSessionSupport(coreComponents.getServletContext());
    }

    private void installSessionSupport(final ServletContext servletContext) {
        Assert.requireNonNull(servletContext, "servletContext");
        final SessionSupport sessionSupport = new SessionSupport();
        servletContext.addListener(sessionSupport);
    }

    private void installServlet(final ServletContext servletContext) {
        Assert.requireNonNull(servletContext, "servletContext");
        final RemotingServlet servlet = new RemotingServlet();
        final ServletRegistration.Dynamic asyncServelt = servletContext.addServlet("Remoting", servlet);
        asyncServelt.setAsyncSupported(true);
        asyncServelt.setLoadOnStartup(1);
        asyncServelt.addMapping("/remoting");
    }
}
