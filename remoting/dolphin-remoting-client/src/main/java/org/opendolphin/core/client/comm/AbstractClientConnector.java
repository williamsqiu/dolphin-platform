/*
 * Copyright 2015-2017 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opendolphin.core.client.comm;

import org.opendolphin.core.client.ClientModelStore;
import org.opendolphin.core.comm.Command;
import org.opendolphin.core.comm.SignalCommand;
import org.opendolphin.util.DolphinRemotingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractClientConnector implements ClientConnector {

    private static final Logger LOG = Logger.getLogger(AbstractClientConnector.class.getName());

    private final Executor uiExecutor;

    private final Executor backgroundExecutor;

    private final RemotingExceptionHandler remotingExceptionHandler;

    private final ClientResponseHandler responseHandler;

    private final ICommandBatcher commandBatcher;

    /**
     * whether listening for push events should be done at all.
     */
    protected final AtomicBoolean pushEnabled = new AtomicBoolean(false);

    /**
     * whether we currently wait for push events (internal state) and may need to release
     */
    protected final AtomicBoolean releaseNeeded = new AtomicBoolean(false);

    protected final AtomicBoolean brokenConnectionFlag = new AtomicBoolean(false);

    protected boolean brokenConnectionFlagForUiExecutor = false;

    /**
     * The named command that waits for pushes on the server side
     */
    private Command pushListener;
    /**
     * The signal command that publishes a "release" event on the respective bus
     */
    private Command releaseCommand;

    protected AbstractClientConnector(final ClientModelStore clientModelStore, final Executor uiExecutor, final ICommandBatcher commandBatcher, RemotingExceptionHandler remotingExceptionHandler, Executor backgroundExecutor) {
        this.uiExecutor = Objects.requireNonNull(uiExecutor);
        this.commandBatcher = Objects.requireNonNull(commandBatcher);
        this.remotingExceptionHandler = Objects.requireNonNull(remotingExceptionHandler);
        this.backgroundExecutor = Objects.requireNonNull(backgroundExecutor);
        this.responseHandler = new ClientResponseHandler(clientModelStore);
        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                commandProcessing();
            }
        });
    }

    private void handleError(Exception exception) {
        Objects.requireNonNull(exception);

        brokenConnectionFlag.set(true);
        stopPushListening();

        uiExecutor.execute(new Runnable() {
            @Override
            public void run() {
                brokenConnectionFlagForUiExecutor = true;
                if(exception instanceof DolphinRemotingException) {
                    remotingExceptionHandler.handle((DolphinRemotingException) exception);
                } else {
                    remotingExceptionHandler.handle(new DolphinRemotingException("internal remoting error", exception));
                }
            }
        });
    }

    protected void commandProcessing() {
        while (!brokenConnectionFlag.get()) {
            try {
                final List<CommandAndHandler> toProcess = commandBatcher.getWaitingBatches().getVal();
                List<Command> commands = new ArrayList<>();
                for (CommandAndHandler c : toProcess) {
                    commands.add(c.getCommand());
                }
                if (LOG.isLoggable(Level.INFO)) {
                    LOG.info("C: sending batch of size " + commands.size());
                    for (Command command : commands) {
                        LOG.info("C:           -> " + command);
                    }
                }
                final List<? extends Command> answers = transmit(commands);

                uiExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        processResults(answers, toProcess);
                    }
                });
            } catch (Exception e) {
                if(!brokenConnectionFlag.get()) {
                    handleError(e);
                } else {
                    LOG.log(Level.WARNING,"Remoting error based on broken connection in parallel request", e);
                }

            }
        }
    }

    protected abstract List<Command> transmit(List<Command> commands) throws DolphinRemotingException;

    @Override
    public void send(final Command command, final OnFinishedHandler callback, final HandlerType handlerType) {
        if(brokenConnectionFlagForUiExecutor) {
            //TODO: Change to DolphinRemotingException
            throw new IllegalStateException("Conection is broken");
        }
        // we have some change so regardless of the batching we may have to release a push
        if (!command.equals(pushListener)) {
            release();
        }
        // we are inside the UI thread and events calls come in strict order as received by the UI toolkit
        CommandAndHandler handler = new CommandAndHandler(command, callback, handlerType);
        commandBatcher.batch(handler);
    }

    @Override
    public void send(final Command command, final OnFinishedHandler callback) {
        send(command, callback, HandlerType.UI);
    }

    @Override
    public void send(Command command) {
        send(command, null);
    }

    protected void processResults(final List<? extends Command> response, List<CommandAndHandler> commandsAndHandlers) {
        if (LOG.isLoggable(Level.INFO)) {
            final List<String> commands = new ArrayList<>();
            if (response != null) {
                for (Command c : response) {
                    commands.add(c.getId());
                }
                LOG.info("C: server responded with {}" + response.size() + " command(s): {}" + commands);
            }
        }

        for (Command serverCommand : response) {
            dispatchHandle(serverCommand);
        }
        OnFinishedHandler callback = commandsAndHandlers.get(0).getHandler();
        if (callback != null) {
            callback.onFinished();
        }
    }

    protected void dispatchHandle(Command command) {
        responseHandler.dispatchHandle(command);
    }

    /**
     * listens for the pushListener to return. The pushListener must be set and pushEnabled must be true.
     */
    protected void listen() {
        if(brokenConnectionFlag.get()) {
            return;
        }

        if (!pushEnabled.get() || releaseNeeded.get()) {
            return;
        }
        releaseNeeded.set(true);
        try {
            send(pushListener, new OnFinishedHandler() {
                @Override
                public void onFinished() {
                    releaseNeeded.set(false);
                    listen();
                }
            });
        } catch (Exception e) {
            // do nothing...
        }
    }

    /**
     * Release the current push listener, which blocks the sending queue.
     * Does nothing in case that the push listener is not active.
     */
    private void release() {
        if (!releaseNeeded.get()) {
            return; // there is no point in releasing if we do not wait. Avoid excessive releasing.
        }

        releaseNeeded.set(false);// release is under way
        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    transmit(Collections.singletonList(releaseCommand));
                } catch (DolphinRemotingException e) {
                    handleError(e);
                }
            }
        });
    }

    @Override
    public void startPushListening(final Command startLongPollCommand, final SignalCommand interruptLongPollCommand) {
        pushListener = Objects.requireNonNull(startLongPollCommand);
        releaseCommand = Objects.requireNonNull(interruptLongPollCommand);

        pushEnabled.set(true);
        listen();
    }

    @Override
    public void stopPushListening() {
        pushEnabled.set(false);
    }

    public void setStrictMode(boolean strictMode) {
        this.responseHandler.setStrictMode(strictMode);
    }

    protected Command getReleaseCommand() {
        return releaseCommand;
    }
}
