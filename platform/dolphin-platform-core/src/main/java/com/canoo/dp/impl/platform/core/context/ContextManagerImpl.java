package com.canoo.dp.impl.platform.core.context;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.context.Context;
import com.canoo.platform.core.context.ContextManager;
import com.canoo.platform.core.functional.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.canoo.dp.impl.platform.core.PlatformConstants.APPLICATION_CONTEXT;
import static com.canoo.dp.impl.platform.core.PlatformConstants.CANONICAL_HOST_NAME_CONTEXT;
import static com.canoo.dp.impl.platform.core.PlatformConstants.HOST_ADDRESS_CONTEXT;
import static com.canoo.dp.impl.platform.core.PlatformConstants.HOST_NAME_CONTEXT;
import static com.canoo.dp.impl.platform.core.PlatformConstants.UNNAMED_APPLICATION;

public class ContextManagerImpl implements ContextManager {

    private final static Logger LOG = LoggerFactory.getLogger(ContextManagerImpl.class);

    private final static ContextManagerImpl INSTANCE = new ContextManagerImpl();

    private final Set<Context> globalContexts;

    private final Lock globalContextsLock;

    private final ThreadLocal<Set<Context>> threadContexts;

    public ContextManagerImpl() {
        globalContexts = new HashSet<>();
        threadContexts = new ThreadLocal<>();
        globalContextsLock = new ReentrantLock();

        addGlobalContext(APPLICATION_CONTEXT, UNNAMED_APPLICATION);

        try {
            final InetAddress address = InetAddress.getLocalHost();
            addGlobalContext(HOST_NAME_CONTEXT, address.getHostName());
            addGlobalContext(CANONICAL_HOST_NAME_CONTEXT, address.getCanonicalHostName());
            addGlobalContext(HOST_ADDRESS_CONTEXT, address.getHostAddress());
        } catch (Exception e) {
            LOG.error("Can not define InetAddress for context!", e);
        }

    }

    @Override
    public Subscription addGlobalContext(final String type, final String value) {
        Assert.requireNonNull(type, "type");
        final Context context = new ContextImpl(type, value);
        globalContextsLock.lock();
        try {
            if(globalContexts.contains(context)) {
                globalContexts.remove(context);
            }
            globalContexts.add(context);
        } finally {
            globalContextsLock.unlock();
        }
        return () -> {
            globalContextsLock.lock();
            try {
                globalContexts.remove(context);
            } finally {
                globalContextsLock.unlock();
            }
        };
    }

    @Override
    public Subscription addThreadContext(final String type, String value) {
        Assert.requireNonNull(type, "type");
        final Set<Context> set = getOrCreateThreadContexts();
        final Context context = new ContextImpl(type, value);
        if(set.contains(context)) {
            set.remove(context);
        }
        set.add(context);
        return () -> set.remove(context);
    }

    @Override
    public Set<Context> getGlobalContexts() {
        globalContextsLock.lock();
        try {
            return Collections.unmodifiableSet(globalContexts);
        } finally {
            globalContextsLock.unlock();
        }
    }

    @Override
    public Set<Context> getThreadContexts() {
        return Collections.unmodifiableSet(getOrCreateThreadContexts());
    }

    private Set<Context> getOrCreateThreadContexts() {
        final Set<Context> set = Optional.ofNullable(threadContexts.get()).orElse(new HashSet<>());
        threadContexts.set(set);
        return set;
    }

    public static ContextManagerImpl getInstance() {
        return INSTANCE;
    }
}
