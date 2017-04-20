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
package org.opendolphin.core.server.comm

import org.junit.Assert
import org.opendolphin.core.comm.Command

class ActionRegistryTests extends GroovyTestCase {

    private class TestDataCommand extends Command {}

    private ActionRegistry registry

    @Override
    protected void setUp() throws Exception {
        registry = new ActionRegistry();
    }

    void testRegisterCommand() {
        Assert.assertEquals(0, registry.getActions().size());

        CommandHandler firstAction = new CommandHandler<Command>() {
            @Override
            void handleCommand(Command command, List response) {

            }
        };
        registry.register(TestDataCommand.class, firstAction);
        Assert.assertEquals(1, registry.getActionsFor(TestDataCommand.class).size());
        Assert.assertTrue(registry.getActionsFor(TestDataCommand.class).contains(firstAction));
        Assert.assertEquals(1, registry.getActions().size());
    }

    void testRegisterCommandHandler(){
        CommandHandler<TestDataCommand> commandHandler = new CommandHandler<TestDataCommand>() {

            @Override
            void handleCommand(TestDataCommand command, List response) {

            }
        };
        registry.register(TestDataCommand.class, commandHandler);
        Assert.assertTrue(registry.getActionsFor(TestDataCommand.class).contains(commandHandler));
        Assert.assertEquals(1, registry.getActions().size());
        Assert.assertEquals(1, registry.getActionsFor(TestDataCommand.class).size());
    }

    void testRegisterCommand_MultipleCalls() {
        Assert.assertEquals(0, registry.getActions().size());

        CommandHandler action = new CommandHandler<Command>() {
            @Override
            void handleCommand(Command command, List<Command> response) {

            }
        }
        registry.register(TestDataCommand.class, action);
        Assert.assertEquals(1, registry.getActions().size());
        Assert.assertEquals(1, registry.getActionsFor(TestDataCommand.class).size());

        registry.register(TestDataCommand.class, action);
        Assert.assertEquals(1, registry.getActionsFor(TestDataCommand.class).size());
    }

}
