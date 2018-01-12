package com.canoo.dp.impl.server.error;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.ReflectionHelper;
import com.canoo.platform.remoting.server.error.ActionExceptionEvent;
import com.canoo.platform.remoting.server.error.ActionExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ActionErrorHandler {

    private final static Logger LOG = LoggerFactory.getLogger(ActionErrorHandler.class);

    public <T extends Throwable> boolean handle(final T throwable, final Object controller, final String controllerName, final String actionName) {
        Assert.requireNonNull(throwable, "throwable");
        Assert.requireNonNull(controller, "controller");
        Assert.requireNonBlank(controllerName, "controllerName");
        Assert.requireNonBlank(actionName, "actionName");

        final ActionExceptionEventImpl exceptionEvent = new ActionExceptionEventImpl(actionName, controllerName, throwable);
        final List<Consumer<ActionExceptionEvent<T>>> consumers = new ArrayList<>();
        consumers.addAll(getConsumersForTypeInController(throwable.getClass(), controller));
        consumers.addAll(getConsumersForType(throwable.getClass()));
        LOG.debug("Found {} handlers to handle action exception of type {}", consumers.size(), throwable.getClass());

        final Iterator<Consumer<ActionExceptionEvent<T>>> iterator = consumers.iterator();
        while (!exceptionEvent.isAborted() && iterator.hasNext()) {
            final Consumer<ActionExceptionEvent<T>> handler = iterator.next();
            try {
                handler.accept(exceptionEvent);
            } catch (Exception e) {
                LOG.error("Error in calling exception handler for error of type '" + throwable.getClass() +
                        "' thrown by action '" + exceptionEvent.getActionName() +
                        "' of controller '" + exceptionEvent.getControllerName() + "'!", e);
            }
        }
        return exceptionEvent.isAborted();
    }

    private <T extends Throwable> List<Consumer<ActionExceptionEvent<T>>> getConsumersForTypeInController(Class<? extends Throwable> throwableClass, Object controller) {
        Assert.requireNonNull(controller, "controller");
        return ReflectionHelper.getInheritedDeclaredMethods(controller.getClass()).stream().
                filter(m -> m.isAnnotationPresent(ActionExceptionHandler.class)).
                filter(m -> m.getParameterCount() == 1).
                filter(m -> m.getParameterTypes()[0].isAssignableFrom(throwableClass)).
                sorted((m1, m2) -> {
                    final int ordinal1 = m1.getAnnotation(ActionExceptionHandler.class).ordinal();
                    final int ordinal2 = m2.getAnnotation(ActionExceptionHandler.class).ordinal();
                    return Integer.compare(ordinal1, ordinal2);
                }).
                map(m -> this.<T>createConsumer(m, controller)).
                collect(Collectors.toList());
    }

    private <T extends Throwable> List<Consumer<ActionExceptionEvent<T>>> getConsumersForType(Class<? extends Throwable> throwableClass) {
        return Collections.emptyList();
    }

    private <T extends Throwable> Consumer<ActionExceptionEvent<T>> createConsumer(final Method method, final Object instance) {
        Assert.requireNonNull(method, "method");
        Assert.requireNonNull(instance, "instance");
        return throwable -> ReflectionHelper.invokePrivileged(method, instance, throwable);
    }
}
