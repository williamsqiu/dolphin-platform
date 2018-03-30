package com.canoo.platform.core.context;

import com.canoo.platform.core.functional.Subscription;

import java.util.Set;

public interface ContextManager {

    Subscription addGlobalContext(String type, String value);

    Subscription addThreadContext(String type, String value);

    Set<Context> getGlobalContexts();

    Set<Context> getThreadContexts();


}
