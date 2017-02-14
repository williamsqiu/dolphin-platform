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

import org.opendolphin.core.RemotingConstants;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.comm.Command;
import org.opendolphin.core.comm.NamedCommand;
import org.opendolphin.core.comm.SignalCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractClientConnector implements ClientConnector {

    private static final Logger LOG = Logger.getLogger(AbstractClientConnector.class.getName());

    private final Executor uiExecutor;

    private final Executor backgroundExecutor = Executors.newCachedThreadPool();

    private ExceptionHandler onException;

    private final ClientResponseHandler responseHandler;

    private final ClientDolphin clientDolphin;

    private final ICommandBatcher commandBatcher;

    /**
     * The named command that waits for pushes on the server side
     */
    private Command pushListener = new NamedCommand(RemotingConstants.POLL_EVENT_BUS_COMMAND_NAME);

    /**
     * The signal command that publishes a "release" event on the respective bus
     */
    private Command releaseCommand = new SignalCommand(RemotingConstants.RELEASE_EVENT_BUS_COMMAND_NAME);

    /**
     * whether listening for push events should be done at all.
     */
    protected AtomicBoolean pushEnabled = new AtomicBoolean(false);

    /**
     * whether we currently wait for push events (internal state) and may need to release
     */
    protected AtomicBoolean releaseNeeded = new AtomicBoolean(false);

    public AbstractClientConnector(ClientDolphin clientDolphin, Executor uiExecutor) {
        this(clientDolphin, uiExecutor, null);
    }

    public AbstractClientConnector(final ClientDolphin clientDolphin, final Executor uiExecutor, final ICommandBatcher commandBatcher) {
        this.clientDolphin = clientDolphin;
        this.uiExecutor = uiExecutor;
        this.commandBatcher = commandBatcher != null ? commandBatcher : new CommandBatcher();
        this.responseHandler = new ClientResponseHandler(clientDolphin);
        onException = new ExceptionHandler() {
            @Override
            public void handle(final Throwable e) {
                LOG.log(Level.SEVERE, "onException reached, rethrowing in UI Thread, consider setting AbstractClientConnector.onException", e);
                if (uiExecutor != null) {
                    uiExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            throw new RuntimeException(e);
                        }
                    });
                } else {
                    LOG.log(Level.SEVERE, "UI Thread not defined...", e);
                }
            }
        };
        startCommandProcessing();
    }

    protected void startCommandProcessing() {
        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    doExceptionSafe(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                final List<CommandAndHandler> toProcess = commandBatcher.getWaitingBatches().getVal();
                                List<Command> commands = new ArrayList<>();
                                for (CommandAndHandler c : toProcess) {
                                    commands.add(c.getCommand());
                                }
                                if (LOG.isLoggable(Level.INFO)) {
                                    LOG.info("C: sending batch of size " + ((ArrayList<Command>) commands).size());
                                    for (Command command : commands) {
                                        LOG.info("C:           -> " + command);
                                    }
                                }
                                final List<? extends Command> answer = transmit(commands);
                                doSafelyInsideUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        processResults(answer, toProcess);
                                    }

                                });
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        });
    }

    protected abstract List<Command> transmit(List<Command> commands);

    @Override
    public void send(Command command, OnFinishedHandler callback) {
        // we have some change so regardless of the batching we may have to release a push
        if (!command.equals(pushListener)) {
            release();
        }
        // we are inside the UI thread and events calls come in strict order as received by the UI toolkit
        CommandAndHandler handler = new CommandAndHandler();
        handler.setCommand(command);
        handler.setHandler(callback);
        commandBatcher.batch(handler);
    }

    @Override
    public void send(Command command) {
        send(command, null);
    }

    protected void processResults(final List<? extends Command> response, List<CommandAndHandler> commandsAndHandlers) {
        // see http://jira.codehaus.org/browse/GROOVY-6946
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

    private void doExceptionSafe(Runnable processing, Runnable atLeast) {
        try {
            processing.run();
        } catch (Exception e) {
            onException.handle(e);
        } finally {
            if (atLeast != null) {
                atLeast.run();
            }
        }
    }

    private void doExceptionSafe(Runnable processing) {
        doExceptionSafe(processing, null);
    }

    private void doSafelyInsideUiThread(final Runnable whatToDo) {
        doExceptionSafe(new Runnable() {
            @Override
            public void run() {
                if (uiExecutor != null) {
                    uiExecutor.execute(whatToDo);
                } else {
                    LOG.warning("please provide howToProcessInsideUI handler");
                    whatToDo.run();
                }
            }
        });
    }

    /**
     * listens for the pushListener to return. The pushListener must be set and pushEnabled must be true.
     */
    protected void listen() {
        if (!pushEnabled.get() || releaseNeeded.get()) {
            return;
        }
        releaseNeeded.set(true);
        send(pushListener, new OnFinishedHandler() {
            @Override
            public void onFinished() {
                releaseNeeded.set(false);
                listen();
            }
        });
    }

    /**
     * Release the current push listener, which blocks the sending queue.
     * Does nothing in case that the push listener is not active.
     */
    protected void release() {
        if (!releaseNeeded.get()) {
            return; // there is no point in releasing if we do not wait. Avoid excessive releasing.
        }

        releaseNeeded.set(false);// release is under way
        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                transmit(Collections.singletonList(releaseCommand));
            }

        });
    }

    @Override
    public void startPushListening() {
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

    public void setOnException(ExceptionHandler onException) {
        this.onException = onException;
    }

    protected Command getReleaseCommand() {
        return releaseCommand;
    }
}
