package com.canoo.impl.dp.spring.test;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.remoting.legacy.commands.StartLongPollCommand;
import com.canoo.dp.impl.remoting.legacy.communication.Command;
import com.canoo.dp.impl.server.client.ClientSessionProvider;
import com.canoo.dp.impl.server.config.RemotingConfiguration;
import com.canoo.dp.impl.server.context.DolphinContext;
import com.canoo.dp.impl.server.controller.ControllerRepository;
import com.canoo.dp.impl.server.legacy.communication.CommandHandler;
import com.canoo.platform.core.functional.Callback;
import com.canoo.platform.server.client.ClientSession;
import com.canoo.platform.server.spi.components.ManagedBeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hendrikebbers on 05.12.17.
 */
public class TestDolphinContext extends DolphinContext {

    private BlockingQueue<Runnable> callLaterTasks = new LinkedBlockingQueue<>();

    public TestDolphinContext(RemotingConfiguration configuration, ClientSession clientSession, ClientSessionProvider clientSessionProvider, ManagedBeanFactory beanFactory, ControllerRepository controllerRepository, Callback<DolphinContext> onDestroyCallback) {
        super(configuration, clientSession, clientSessionProvider, beanFactory, controllerRepository, onDestroyCallback);

        getServerConnector().getRegistry().register(PingCommand.class, new CommandHandler() {
            @Override
            public void handleCommand(Command command, List response) {

            }
        });
    }

    @Override
    public List<Command> handle(List<Command> commands) {
        final List<Command> commandsWithFackedLongPool = new ArrayList<>(commands);
        commandsWithFackedLongPool.add(new StartLongPollCommand());

        return super.handle(commandsWithFackedLongPool);
    }

    @Override
    protected void onPollEventBus() {
        while (!callLaterTasks.isEmpty()) {
            callLaterTasks.poll().run();
        }
    }

    @Override
    public <T> Future<T> callLater(final Callable<T> callable) {
        Assert.requireNonNull(callable, "callable");
        final CompletableFuture<T> result = new CompletableFuture<T>();
        callLaterTasks.offer(() -> {
            try {
                T taskResult = callable.call();
                result.complete(taskResult);
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        });
        return result;
    }
}
