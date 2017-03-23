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

import org.opendolphin.core.client.ClientAttribute
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.comm.OnFinishedHandler
import org.opendolphin.core.server.*
import org.opendolphin.core.server.action.DolphinServerAction
import org.opendolphin.core.server.comm.ActionRegistry
import org.opendolphin.core.server.comm.CommandHandler

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.util.concurrent.TimeUnit
/**
 * Functional tests for scenarios that customers observed when controlling
 * the application purely from server side.
 */

class ServerControlledFunctionalTests extends GroovyTestCase {

    private final class CreatePmCommand extends Command {}
    private final class CheckPmIsThereCommand extends Command {}
    private final class DeleteAndRecreateCommand extends Command {}
    private final class AssertRetainedServerStateCommand extends Command {}
    private final class ChangeValueMultipleTimesAndBackToBaseCommand extends Command {}
    private final class RemoveCommand extends Command {}
    private final class SetAndUnsetQualifierCommand extends Command {}
    private final class SetQualifierCommand extends Command {}



    volatile TestInMemoryConfig context
    DefaultServerDolphin serverDolphin
    ClientDolphin clientDolphin

    @Override
    protected void setUp() {
        context = new TestInMemoryConfig()
        serverDolphin = context.serverDolphin
        clientDolphin = context.clientDolphin
//        LogConfig.noLogs()
    }

    @Override
    protected void tearDown() {
        assert context.done.await(20, TimeUnit.SECONDS)
    }

    public <T extends Command> void registerAction(ServerDolphin serverDolphin, Class<T> commandType, CommandHandler<T> handler) {
        serverDolphin.register(new DolphinServerAction() {

            @Override
            void registerIn(ActionRegistry registry) {
                registry.register(commandType, handler);
            }
        });
    }

    void testPMsWereDeletedAndRecreated() {
        // a pm created on the client side
        clientDolphin.presentationModel("pm1", new ClientAttribute("a", 0 ))

        // register a server-side action that sees the second PM
        registerAction (serverDolphin, CheckPmIsThereCommand.class, { cmd, list ->
            assert serverDolphin.getPresentationModel("pm1").getAttribute("a").value == 1
            assert clientDolphin.getPresentationModel("pm1").getAttribute("a").value == 1
            context.assertionsDone()
        });

        assert clientDolphin.getPresentationModel("pm1").getAttribute("a").value == 0
        clientDolphin.delete(clientDolphin.getPresentationModel("pm1"))
        clientDolphin.presentationModel("pm1", new ClientAttribute("a", 1 ))
        clientDolphin.send(new CheckPmIsThereCommand(), null)
    }


    void testPMsWereCreatedOnServerSideDeletedByTypeRecreatedOnServer() { // the "Baerbel" problem
        registerAction( serverDolphin, CreatePmCommand.class, { cmd, list ->
            serverDolphin.presentationModel(null, "myType", new DTO(new Slot('a',0)))
        });
        registerAction( serverDolphin, DeleteAndRecreateCommand.class, { cmd, list ->
            List<ServerPresentationModel> toDelete = new ArrayList<>();
            for(ServerPresentationModel model : serverDolphin.findAllPresentationModelsByType("myType")) {
                toDelete.add(model);
            }
            for(ServerPresentationModel model : toDelete) {
                serverDolphin.removePresentationModel(model);
            }

            serverDolphin.presentationModel(null, "myType", new DTO(new Slot('a',0))) // recreate
            serverDolphin.presentationModel(null, "myType", new DTO(new Slot('a',1))) // recreate

            assert serverDolphin.findAllPresentationModelsByType("myType").size() == 2
            assert serverDolphin.findAllPresentationModelsByType("myType")[0].getAttribute("a").value == 0
            assert serverDolphin.findAllPresentationModelsByType("myType")[1].getAttribute("a").value == 1
        });

        registerAction( serverDolphin, AssertRetainedServerStateCommand.class, { cmd, list ->
            assert serverDolphin.findAllPresentationModelsByType("myType").size() == 2
            assert serverDolphin.findAllPresentationModelsByType("myType")[0].getAttribute("a").value == 0
            assert serverDolphin.findAllPresentationModelsByType("myType")[1].getAttribute("a").value == 1
            context.assertionsDone()
        });

        clientDolphin.send(new CreatePmCommand(), new OnFinishedHandler() {
            @Override
            void onFinished() {
                assert clientDolphin.findAllPresentationModelsByType("myType").size() == 1
                assert clientDolphin.findAllPresentationModelsByType("myType").first().getAttribute("a").value == 0
            }
        });
        clientDolphin.send(new DeleteAndRecreateCommand(), new OnFinishedHandler() {
            @Override
            void onFinished() {
                assert clientDolphin.findAllPresentationModelsByType("myType").size() == 2
                assert clientDolphin.findAllPresentationModelsByType("myType")[0].getAttribute("a").value == 0
                assert clientDolphin.findAllPresentationModelsByType("myType")[1].getAttribute("a").value == 1
            }
        });
        clientDolphin.send(new AssertRetainedServerStateCommand(), null)
    }

    void testChangeValueMultipleTimesAndBackToBase() { // Alex issue
        // register a server-side action that creates a PM
        registerAction( serverDolphin, (CreatePmCommand.class), { cmd, list ->
            serverDolphin.presentationModel("myPm", null, new DTO(new Slot('a',0)))
        });
        registerAction( serverDolphin, ChangeValueMultipleTimesAndBackToBaseCommand.class, { cmd, list ->
            def myPm = serverDolphin.getPresentationModel("myPm")
            myPm.getAttribute("a").value = 1
            myPm.getAttribute("a").value = 2
            myPm.getAttribute("a").value = 0
        });

        clientDolphin.send(new CreatePmCommand(), null)
        clientDolphin.send(new ChangeValueMultipleTimesAndBackToBaseCommand(), new OnFinishedHandler() {
            @Override
            void onFinished() {
                def myPm = clientDolphin.getPresentationModel("myPm")
                assert myPm.getAttribute("a").value == 0
                context.assertionsDone()
            }
        });
    }

    void testServerSideRemove() {
        registerAction(serverDolphin, CreatePmCommand.class, { cmd, list ->
            serverDolphin.presentationModel("myPm", null, new DTO(new Slot('a',0)))
        });
        registerAction(serverDolphin, RemoveCommand.class, { cmd, list ->
            def myPm = serverDolphin.getPresentationModel("myPm")
            serverDolphin.removePresentationModel(myPm)
            assert null == serverDolphin.getPresentationModel("myPm")
        });

        clientDolphin.send(new CreatePmCommand(), new OnFinishedHandler() {
            @Override
            void onFinished() {
                assert clientDolphin.getPresentationModel("myPm")
            }
        });
        clientDolphin.send(new RemoveCommand(), new OnFinishedHandler() {
            @Override
            void onFinished() {
                assert null == clientDolphin.getPresentationModel("myPm")
                context.assertionsDone()
            }
        });
    }

    void testServerSideSetAndUnsetQualifier() {
        registerAction(serverDolphin, CreatePmCommand.class, { cmd, list ->
            serverDolphin.presentationModel(null, "myType", new DTO(new Slot('a',0)))
        });
        registerAction(serverDolphin, SetAndUnsetQualifierCommand.class, { cmd, list ->
            def myPm = serverDolphin.findAllPresentationModelsByType("myType").first()
            myPm.getAttribute("a").qualifier = "myQualifier"
            myPm.getAttribute("a").qualifier = "othervalue"
        });

        clientDolphin.send(new CreatePmCommand(), new OnFinishedHandler() {
            @Override
            void onFinished() {
                def pm = clientDolphin.findAllPresentationModelsByType("myType").first()
                pm.getAttribute('a').addPropertyChangeListener("qualifier", new PropertyChangeListener() {
                    @Override
                    void propertyChange(PropertyChangeEvent evt) { // assume a client side listener
                        pm.getAttribute('a').qualifier="myQualifier"
                    }
                })
            }
        });
        clientDolphin.send(new SetAndUnsetQualifierCommand(), new OnFinishedHandler() {
            @Override
            void onFinished() {
                assert "myQualifier" == clientDolphin.findAllPresentationModelsByType("myType").first().getAttribute("a").qualifier
                context.assertionsDone()
            }
        });
    }

    void testServerSideSetQualifier() {
        registerAction( serverDolphin, CreatePmCommand.class, { cmd, list ->
            serverDolphin.presentationModel(null, "myType", new DTO(new Slot('a',0)))
            serverDolphin.presentationModel("target", null, new DTO(new Slot('a',1)))
        });
        registerAction( serverDolphin, SetQualifierCommand.class, { cmd, list ->
            def myPm = serverDolphin.findAllPresentationModelsByType("myType").first()
            myPm.getAttribute("a").qualifier = "myQualifier"
        })

        clientDolphin.send(new CreatePmCommand(), new OnFinishedHandler() {
            @Override
            void onFinished() {
                assert clientDolphin.findAllPresentationModelsByType("myType").first()
            }
        });
        clientDolphin.send(new SetQualifierCommand(), new OnFinishedHandler() {
            @Override
            void onFinished() {
                assert clientDolphin.findAllPresentationModelsByType("myType").first().getAttribute("a").qualifier == "myQualifier"
                context.assertionsDone()
            }
        });
    }

}