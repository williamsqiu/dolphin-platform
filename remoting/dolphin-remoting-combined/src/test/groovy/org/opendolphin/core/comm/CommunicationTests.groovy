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
package org.opendolphin.core.comm

import org.junit.Assert
import org.opendolphin.LogConfig
import org.opendolphin.core.client.ClientAttribute
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientModelStore
import org.opendolphin.core.client.ClientPresentationModel
import org.opendolphin.core.client.comm.AbstractClientConnector
import org.opendolphin.core.server.ServerConnector
import org.opendolphin.core.server.comm.CommandHandler

import java.util.concurrent.TimeUnit
import java.util.logging.Level

/**
 * Tests for the sequence between client requests and server responses.
 * They are really more integration tests than unit tests.
 */

class CommunicationTests extends GroovyTestCase {

    private ServerConnector serverConnector;
    private AbstractClientConnector clientConnector;
    private ClientModelStore clientModelStore;
    private ClientDolphin clientDolphin;
    private TestInMemoryConfig config;

    private final class ButtonActionCommand extends Command {}


    @Override
    protected void setUp() {
        LogConfig.logOnLevel(Level.INFO);
        config = new TestInMemoryConfig();
        serverConnector = config.getServerDolphin().getServerConnector();
        clientConnector = config.getClientConnector();
        clientModelStore = config.getClientDolphin().getModelStore();
        clientDolphin = config.getClientDolphin();
    }

    @Override
    protected void tearDown() {
        Assert.assertTrue(config.getDone().await(2, TimeUnit.SECONDS));
    }

    void testSimpleAttributeChangeIsVisibleOnServer() {
        ClientAttribute ca = new ClientAttribute('name', null)
        ClientPresentationModel cpm = new ClientPresentationModel('model', Arrays.asList(ca));
        clientModelStore.add(cpm);

        Command receivedCommand = null;
        CommandHandler testServerAction = new CommandHandler<ValueChangedCommand>() {

            @Override
            void handleCommand(ValueChangedCommand command, List<Command> response) {
                receivedCommand = command;
            }
        }
        serverConnector.getRegistry().register(ValueChangedCommand.class, testServerAction);
        ca.setValue('initial');

        clientDolphin.sync {
            Assert.assertNotNull(receivedCommand);
            Assert.assertEquals('ValueChanged', receivedCommand.getId());
            Assert.assertEquals(ValueChangedCommand.class, receivedCommand.getClass());

            ValueChangedCommand cmd = receivedCommand;
            Assert.assertEquals(null, cmd.getOldValue());
            Assert.assertEquals('initial', cmd.getNewValue());
            config.assertionsDone();
        }
    }

    void testServerIsNotifiedAboutNewAttributesAndTheirPms() {
        Command receivedCommand = null
        CommandHandler testServerAction = new CommandHandler<CreatePresentationModelCommand>() {

            @Override
            void handleCommand(CreatePresentationModelCommand command, List<Command> response) {
                receivedCommand = command
            }
        };
        serverConnector.getRegistry().register(CreatePresentationModelCommand.class, testServerAction);
        clientModelStore.add(new ClientPresentationModel('testPm', Arrays.asList(new ClientAttribute('name', null))));
        clientDolphin.sync {
            Assert.assertNotNull(receivedCommand);
            Assert.assertEquals('CreatePresentationModel', receivedCommand.getId());
            Assert.assertEquals(CreatePresentationModelCommand.class, receivedCommand.getClass());
            CreatePresentationModelCommand cmd = receivedCommand;
            Assert.assertEquals('testPm', cmd.getPmId());

            config.assertionsDone();
        }
    }

    void testWhenServerChangesValueThisTriggersUpdateOnClient() {
        ClientAttribute ca = new ClientAttribute('name', null);

        CommandHandler setValueAction = new CommandHandler<CreatePresentationModelCommand>() {

            @Override
            void handleCommand(CreatePresentationModelCommand command, List<Command> response) {
                response.add(new ValueChangedCommand(command.attributes.id.first(), null, "set from server"));
            }
        };

        Command receivedCommand = null
        CommandHandler valueChangedAction = new CommandHandler<ValueChangedCommand>() {

            @Override
            void handleCommand(ValueChangedCommand command, List<Command> response) {
                receivedCommand = command
                clientDolphin.sync {
                    // there is no onFinished for value changes, so we have to do it here
                    Assert.assertEquals("set from server", ca.getValue()); // client is updated
                    Assert.assertEquals(ca.id, receivedCommand.attributeId); // client notified server about value change
                    config.assertionsDone();
                }
            }
        };

        serverConnector.getRegistry().register(CreatePresentationModelCommand.class, setValueAction);
        serverConnector.getRegistry().register(ValueChangedCommand.class, valueChangedAction);
        clientModelStore.add(new ClientPresentationModel('testPm', Arrays.asList(ca))); // trigger the whole cycle
    }


    void testRequestingSomeGeneralCommandExecution() {
        boolean reached = false;
        serverConnector.getRegistry().register(ButtonActionCommand.class, new CommandHandler<ButtonActionCommand>() {

            @Override
            void handleCommand(ButtonActionCommand command, List response) {
                reached = true;
            }
        });
        clientConnector.send(new ButtonActionCommand());

        clientDolphin.sync {
            Assert.assertTrue(reached);
            config.assertionsDone();
        }
    }

}
