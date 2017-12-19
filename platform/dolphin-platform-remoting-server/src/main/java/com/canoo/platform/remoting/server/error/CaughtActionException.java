package com.canoo.platform.remoting.server.error;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

/**
 * Exception wrapper that is used as parameter for error handler methods that are defined by
 * the {@link HandlesActionExceptions} annotation.
 *
 * By default all exception handler will be called in a defined priority (see {@link HandlesActionExceptions})
 * with the same {@link CaughtActionException} instance once an error occurs in a remote action call
 * (see {@link com.canoo.platform.remoting.server.DolphinAction}). By calling the
 * {@link CaughtActionException#abort()} method in an handler all following handlers won't be called and the
 * action call will not be defined as failing. By doing so an error won't be send to the client.
 *
 * @param <T> type of the exception
 */
@API(since = "1.0.0-RC3", status = EXPERIMENTAL)
public interface CaughtActionException<T extends Throwable> {

    /**
     * Returns the exception that is wrapped by this instance. The returned exception is the
     * exception instance that was thrown by the action call.
     * @return the exception
     */
    T getException();

    /**
     * By calling this method no following exception handler will be called.
     */
    void abort();
}
