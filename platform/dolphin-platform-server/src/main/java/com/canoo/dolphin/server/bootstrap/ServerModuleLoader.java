package com.canoo.dolphin.server.bootstrap;

import com.canoo.dolphin.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ServerModuleLoader {

    private static final Logger LOG = LoggerFactory.getLogger(ServerModuleLoader.class);

    private final ServerCoreComponents coreComponents;

    public ServerModuleLoader(final ServerCoreComponents coreComponents) {
        this.coreComponents = Assert.requireNonNull(coreComponents, "coreComponents");
    }

    public void init() throws InitializationException {
        init(coreComponents.getClass().getClassLoader());
    }

    public void init(final ClassLoader classLoader) throws InitializationException {
        Assert.requireNonNull(classLoader, "classLoader");
        final ServiceLoader<ServerModule> serviceLoader = ServiceLoader.load(ServerModule.class, classLoader);
        final Iterator<ServerModule> iterator = serviceLoader.iterator();
        final List<ServerModule> modules = new ArrayList<>();
        while (iterator.hasNext()) {
            modules.add(iterator.next());
        }

        Collections.sort(modules, new Comparator<ServerModule>() {
            @Override
            public int compare(ServerModule o1, ServerModule o2) {
                return Integer.compare(o1.getOrder(), o2.getOrder());
            }
        });

        LOG.debug("Found {} Dolphin Plaform modules", modules.size());

        for (final ServerModule module : modules) {
            try {
                LOG.debug("Will initialize Dolphin Plaform module {}", module.getName());
                if(module.shouldBoot(coreComponents.getConfiguration())) {
                    module.initialize(coreComponents);
                }
            } catch (Exception e) {
                throw new InitializationException("Can not initialize Dolphin Plaform module " + module.getName(), e);
            }
        }
    }

}
