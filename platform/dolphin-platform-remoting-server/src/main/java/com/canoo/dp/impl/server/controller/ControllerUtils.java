package com.canoo.dp.impl.server.controller;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.remoting.server.DolphinAction;
import com.canoo.platform.remoting.server.DolphinController;

import java.lang.reflect.Method;

public class ControllerUtils {


    public static String getActionMethodName(final Method method) {
        Assert.requireNonNull(method, "method");
        if (method.isAnnotationPresent(DolphinAction.class)) {
            DolphinAction actionAnnotation = method.getAnnotation(DolphinAction.class);
            String currentActionName = method.getName();
            if (actionAnnotation.value() != null && !actionAnnotation.value().trim().isEmpty()) {
                currentActionName = actionAnnotation.value();
            }

            return currentActionName;

        } else {
            throw new IllegalArgumentException("Method " + method.getName() + " is not annotated with " + DolphinAction.class);
        }
    }

    public static String getControllerName(final Class<?> clazz) {
        Assert.requireNonNull(clazz, "clazz");
        if (clazz.isAnnotationPresent(DolphinController.class)) {
            DolphinController controllerAnnotation = clazz.getAnnotation(DolphinController.class);
            String currentControllerName = clazz.getName();
            if (controllerAnnotation.value() != null && !controllerAnnotation.value().trim().isEmpty()) {
                currentControllerName = controllerAnnotation.value();
            }

            return currentControllerName;

        } else {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not annotated with " + DolphinController.class);
        }
    }
}
