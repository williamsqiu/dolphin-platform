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
 * a remote action call (see {@link com.canoo.platform.remoting.server.DolphinAction}). A method that is
 * annotated with this annotation must be a non static method with no return type
 * (<code>void</code> method) and must it define exactly one parameter of the type
 * {@link CaughtActionException}.
 *
 * Exception handlers for remoting action calls can be defined in 2 different ways:
 * <ul>
 * <li>A method in a remote controller (see {@link com.canoo.platform.remoting.server.DolphinController})
 * can be used used as an exception handler. Such a handler will handle all exception that are thrown by
 * a action method of the given controller class (or any sub class). A controller class can define
 * multiple methods that are used as exception handlers.</li>
 * <li>A method in a class that is annotated by {@link com.canoo.platform.server.ServerListener}. Such
 * a class will automatically be found the platform and provided in the client scope
 * (see {@link com.canoo.platform.server.client.ClientSession}). A class that is annotated by
 * {@link com.canoo.platform.server.ServerListener} can define multiple exception handlers</li>
 *</ul>
 *
 * Since multiple exception handlers can be defined an exception that is thrown by a remote action call
 * can be handled by several exception handlers. All handlers will be called in a defined priority:
 * <ul>
 *     <li>The handlers that are defined in the controller class (or a super class) that throws the exception will be called first.</li>
 *     <li>All handlers for the controller will be sorted by the
 *     {@link HandlesActionExceptions#priority()} value. The highest value will be called first.</li>
 *     <li>Only handlers of that controller that define the thrown exception type or a super type of that exception by the generic type of the {@link CaughtActionException} parameter will be called.</li>
 *     <li>After the handlers that are defined by the controller all handlers that are defined in
 *     classes that are annotated with {@link com.canoo.platform.server.ServerListener} will
 *     be called.</li>
 *     <li>All handlers in this classes will be sorted by the
 *     {@link HandlesActionExceptions#priority()} value. The highest value will be called first.</li>
 *      <li>Only handlers that define the thrown exception type or a super type of that exception by the generic type of the {@link CaughtActionException} parameter will be called.</li>
 * </ul>
 *
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.METHOD)
@API(since = "0.x", status = EXPERIMENTAL)
public @interface HandlesActionExceptions {

    int priority() default 1;
}
