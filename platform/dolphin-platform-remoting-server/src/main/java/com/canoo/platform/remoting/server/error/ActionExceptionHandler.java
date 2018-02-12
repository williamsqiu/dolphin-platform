package com.canoo.platform.remoting.server.error;

import org.apiguardian.api.API;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apiguardian.api.API.Status.EXPERIMENTAL;

/**
 * This annotation can be used to mark methods as exception handler for an exception that is thrown by
 * a remote action call (see {@link com.canoo.platform.remoting.server.RemotingAction}). A method that is
 * annotated with this annotation must be a non static method with no return type
 * (<code>void</code> method) and must it define exactly one parameter of the type
 * {@link ActionExceptionEvent}.
 *
 * A method in a remote controller (see {@link com.canoo.platform.remoting.server.RemotingController})
 * can be used used as an exception handler. Such a handler will handle all exception that are thrown by
 * a action method of the given controller class (or any sub class). A controller class can define
 * multiple methods that are used as exception handlers.
 *
 * Since multiple exception handlers can be defined an exception that is thrown by a remote action call
 * can be handled by several exception handlers. All handlers will be called in a defined ordinal:
 * <ul>
 *     <li>The handlers that are defined in the controller class (or a super class) that throws the exception will be called first.</li>
 *     <li>All handlers for the controller will be sorted by the
 *     {@link ActionExceptionHandler#ordinal()} value. The highest value will be called first.</li>
 *     <li>Only handlers of that controller that define the thrown exception type or a super type of that exception by the generic type of the {@link ActionExceptionEvent} parameter will be called.</li>
 * </ul>
 *
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.METHOD)
@API(since = "1.0.0-RC4", status = EXPERIMENTAL)
public @interface ActionExceptionHandler {

    int ordinal() default 1;
}
