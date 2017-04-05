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
package com.canoo.dolphin.test.impl;

import com.canoo.dolphin.impl.commands.StartLongPollCommand;
import com.canoo.dolphin.server.context.DolphinContext;
import com.canoo.dolphin.util.Assert;
import org.opendolphin.core.client.ClientModelStore;
import org.opendolphin.core.client.comm.*;
import org.opendolphin.core.comm.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DolphinTestClientConnector extends AbstractClientConnector {

    private final DolphinContext dolphinContext;

    private final Executor uiExecutor;

    public DolphinTestClientConnector(ClientModelStore clientModelStore, Executor uiExecutor, DolphinContext dolphinContext) {
        super(clientModelStore, uiExecutor, new CommandBatcher(), new SimpleExceptionHandler(), Executors.newCachedThreadPool());
        this.dolphinContext = Assert.requireNonNull(dolphinContext, "dolphinContext");
        this.uiExecutor = Assert.requireNonNull(uiExecutor, "uiExecutor");
    }

    @Override
    protected void commandProcessing() {
        /* do nothing! */
    }

    @Override
    public void send(Command command, OnFinishedHandler callback) {
        List<Command> answer = transmit(new ArrayList<>(Arrays.asList(command)));
        CommandAndHandler handler = new CommandAndHandler(command, callback);
        processResults(answer, new ArrayList<>(Arrays.asList(handler)));
    }

    @Override
    public void send(Command command) {
        send(command, null);
    }

    @Override
    protected void listen() {
        //TODO: no implementation since EventBus is used in a different way for this tests. Should be refactored in parent class.
    }

    @Override
    protected List<Command> transmit(List<Command> commands) {
        ArrayList<Command> realCommands = new ArrayList<>(commands);
        realCommands.add(new StartLongPollCommand());
        return dolphinContext.handle(commands);
    }

}
