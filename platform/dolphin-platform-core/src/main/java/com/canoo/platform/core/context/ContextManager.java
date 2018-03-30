package com.canoo.platform.core.context;

import com.canoo.platform.core.functional.Subscription;

import java.util.List;

public interface ContextManager {

    Subscription addGlobalContext(Context context);

    Subscription addThreadContext(Context context);

    void removeGlobalContext(Context context);

    void removeThreadContext(Context context);

    List<Context> getGlobalContexts();

    List<Context> getThreadContexts();

}
