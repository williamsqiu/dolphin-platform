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
package org.opendolphin.core.server

import org.opendolphin.core.ModelStoreEvent
import org.opendolphin.core.ModelStoreListener

public class ServerDolphinTest extends GroovyTestCase {
    DefaultServerDolphin dolphin

    @Override
    protected void setUp() throws Exception {
        dolphin = ServerDolphinFactory.create()
        dolphin.getModelStore().currentResponse = []
    }

    void testListPresentationModels() {
        assert dolphin.getModelStore().listPresentationModelIds().empty
        assert dolphin.getModelStore().listPresentationModels().empty
        assert dolphin.getModelStore().findAllAttributesByQualifier("no-such-qualifier").empty
        assert dolphin.getModelStore().findAllPresentationModelsByType("no-such-type").empty

        def pm1 = new ServerPresentationModel("first", [], dolphin.getModelStore())
        dolphin.getModelStore().add pm1

        assert ['first'].toSet() == dolphin.getModelStore().listPresentationModelIds()
        assert [pm1] == dolphin.getModelStore().listPresentationModels().toList()

        def pm2 = new ServerPresentationModel("second", [], dolphin.getModelStore())
        dolphin.getModelStore().add pm2

        assert 2 == dolphin.getModelStore().listPresentationModelIds().size()
        assert 2 == dolphin.getModelStore().listPresentationModels().size()

        for (id in dolphin.getModelStore().listPresentationModelIds()) {
            assert dolphin.getModelStore().findPresentationModelById(id) in dolphin.getModelStore().listPresentationModels()
        }
    }

    void testAddRemoveModelStoreListener() {
        int typedListenerCallCount = 0
        int listenerCallCount = 0
        ModelStoreListener listener = new ModelStoreListener() {
            @Override
            void modelStoreChanged(ModelStoreEvent event) {
                listenerCallCount++
            }
        }
        ModelStoreListener typedListener = new ModelStoreListener() {
            @Override
            void modelStoreChanged(ModelStoreEvent event) {
                typedListenerCallCount++
            }
        }
        dolphin.getModelStore().addModelStoreListener 'person', typedListener
        dolphin.getModelStore().addModelStoreListener listener
        dolphin.getModelStore().add(new ServerPresentationModel('p1', [], dolphin.getModelStore()))
        ServerPresentationModel modelWithType = new ServerPresentationModel('person1', [], dolphin.getModelStore())
        modelWithType.setPresentationModelType('person')
        dolphin.getModelStore().add(modelWithType)
        dolphin.getModelStore().add(new ServerPresentationModel('p2', [], dolphin.getModelStore()))
        dolphin.getModelStore().removeModelStoreListener 'person', typedListener
        dolphin.getModelStore().removeModelStoreListener listener
        assert 3 == listenerCallCount
        assert 1 == typedListenerCallCount


    }

    void testAddModelStoreListenerWithClosure() {
        int typedListenerCallCount = 0
        int listenerCallCount = 0
        dolphin.getModelStore().addModelStoreListener 'person', { evt -> typedListenerCallCount++ }
        dolphin.getModelStore().addModelStoreListener { evt -> listenerCallCount++ }
        dolphin.getModelStore().add(new ServerPresentationModel('p1', [], dolphin.getModelStore()))
        ServerPresentationModel modelWithType = new ServerPresentationModel('person1', [], dolphin.getModelStore())
        modelWithType.setPresentationModelType('person')
        dolphin.getModelStore().add(modelWithType)
        dolphin.getModelStore().add(new ServerPresentationModel('p2', [], dolphin.getModelStore()))
        assert 3 == listenerCallCount
        assert 1 == typedListenerCallCount
    }

    void testHasModelStoreListener() {
        ModelStoreListener listener = getListener()
        assert !dolphin.getModelStore().hasModelStoreListener(null)
        assert !dolphin.getModelStore().hasModelStoreListener(listener)
        dolphin.getModelStore().addModelStoreListener listener
        assert dolphin.getModelStore().hasModelStoreListener(listener)
        listener = getListener()
        dolphin.getModelStore().addModelStoreListener('person', listener)
        assert !dolphin.getModelStore().hasModelStoreListener('car',listener)
        assert dolphin.getModelStore().hasModelStoreListener('person',listener)
    }

    void testRegisterDefaultActions() {
        dolphin.getServerConnector().registerDefaultActions();
        def numDefaultActions = dolphin.serverConnector.dolphinServerActions.size()

        // multiple calls should not lead to multiple initializations
        dolphin.getServerConnector().registerDefaultActions();
        assert numDefaultActions == dolphin.serverConnector.dolphinServerActions.size()
    }

    private ModelStoreListener getListener() {
        new ModelStoreListener() {
            @Override
            void modelStoreChanged(ModelStoreEvent event) {
                // do nothing
            }
        }
    }
}
