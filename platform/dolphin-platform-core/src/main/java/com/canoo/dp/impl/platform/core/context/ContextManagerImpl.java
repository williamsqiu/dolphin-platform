package com.canoo.dp.impl.platform.core.context;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.core.context.Context;
import com.canoo.platform.core.context.ContextManager;
import com.canoo.platform.core.functional.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.canoo.dp.impl.platform.core.PlatformConstants.CANONICAL_HOST_NAME_CONTEXT;
import static com.canoo.dp.impl.platform.core.PlatformConstants.HOST_ADDRESS_CONTEXT;
import static com.canoo.dp.impl.platform.core.PlatformConstants.HOST_NAME_CONTEXT;

public class ContextManagerImpl implements ContextManager {

    private final static Logger LOG = LoggerFactory.getLogger(ContextManagerImpl.class);

    private final static ContextManagerImpl INSTANCE = new ContextManagerImpl();

    private final List<Context> globalContexts;

    private final ThreadLocal<List<Context>> threadContexts;

    public ContextManagerImpl() {
        globalContexts = new CopyOnWriteArrayList();
        threadContexts = new ThreadLocal<>();

        try {
            final InetAddress address = InetAddress.getLocalHost();
            addGlobalContext(new ContextImpl(HOST_NAME_CONTEXT, address.getHostName()));
            addGlobalContext(new ContextImpl(CANONICAL_HOST_NAME_CONTEXT, address.getCanonicalHostName()));
            addGlobalContext(new ContextImpl(HOST_ADDRESS_CONTEXT, address.getHostAddress()));
        } catch (Exception e) {
            LOG.error("Can not define InetAddress for context!", e);
        }

    }

    @Override
    public Subscription addGlobalContext(final Context context) {
        Assert.requireNonNull(context, "context");
        globalContexts.add(context);
        return () -> globalContexts.remove(context);
    }

    @Override
    public Subscription addThreadContext(final Context context) {
        Assert.requireNonNull(context, "context");
        final List<Context> list = getOrCreateThreadContexts();
        list.add(context);
        return () -> list.remove(context);
    }

    @Override
    public void removeGlobalContext(final Context context) {
        Assert.requireNonNull(context, "context");
        globalContexts.remove(context);
    }

    @Override
    public void removeThreadContext(final Context context) {
        Assert.requireNonNull(context, "context");
        getOrCreateThreadContexts().remove(context);
    }

    @Override
    public List<Context> getGlobalContexts() {
        return Collections.unmodifiableList(globalContexts);
    }

    @Override
    public List<Context> getThreadContexts() {
        return Collections.unmodifiableList(getOrCreateThreadContexts());
    }

    private List<Context> getOrCreateThreadContexts() {
        final List<Context> list = Optional.ofNullable(threadContexts.get()).orElse(new CopyOnWriteArrayList<>());
        threadContexts.set(list);
        return list;
    }

    public static ContextManagerImpl getInstance() {
        return INSTANCE;
    }
}
