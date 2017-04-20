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

import org.junit.Assert
import org.opendolphin.core.ModelStoreEvent
import org.opendolphin.core.ModelStoreListener
import org.opendolphin.core.PresentationModel

public class ServerDolphinTest extends GroovyTestCase {

    private DefaultServerDolphin dolphin;

    @Override
    protected void setUp() throws Exception {
        dolphin = ServerDolphinFactory.create();
        dolphin.getModelStore().setCurrentResponse(new ArrayList<ServerAttribute>());
    }

    void testListPresentationModels() {
        Assert.assertTrue(dolphin.getModelStore().listPresentationModelIds().isEmpty());
        Assert.assertTrue(dolphin.getModelStore().listPresentationModels().isEmpty());
        Assert.assertTrue(dolphin.getModelStore().findAllAttributesByQualifier("no-such-qualifier").isEmpty());
        Assert.assertTrue(dolphin.getModelStore().findAllPresentationModelsByType("no-such-type").isEmpty());

        ServerPresentationModel pm1 = new ServerPresentationModel("first", new ArrayList<ServerAttribute>(), dolphin.getModelStore());
        dolphin.getModelStore().add(pm1);

        Assert.assertEquals(Collections.singleton('first'), dolphin.getModelStore().listPresentationModelIds());
        Assert.assertEquals(1, dolphin.getModelStore().listPresentationModelIds().size());
        Assert.assertEquals(pm1, dolphin.getModelStore().listPresentationModels().iterator().next());

        ServerPresentationModel pm2 = new ServerPresentationModel("second", new ArrayList<ServerAttribute>(), dolphin.getModelStore())
        dolphin.getModelStore().add(pm2);

        Assert.assertEquals(2, dolphin.getModelStore().listPresentationModelIds().size());
        Assert.assertEquals(2, dolphin.getModelStore().listPresentationModels().size());


        for (String id : dolphin.getModelStore().listPresentationModelIds()) {
            PresentationModel model = dolphin.getModelStore().findPresentationModelById(id);
            Assert.assertNotNull(model);
            Assert.assertTrue(dolphin.getModelStore().listPresentationModels().contains(model));
        }
    }

    void testAddRemoveModelStoreListener() {
        int typedListenerCallCount = 0;
        int listenerCallCount = 0;
        ModelStoreListener listener = new ModelStoreListener() {
            @Override
            void modelStoreChanged(ModelStoreEvent event) {
                listenerCallCount++;
            }
        }
        ModelStoreListener typedListener = new ModelStoreListener() {
            @Override
            void modelStoreChanged(ModelStoreEvent event) {
                typedListenerCallCount++;
            }
        }
        dolphin.getModelStore().addModelStoreListener('person', typedListener);
        dolphin.getModelStore().addModelStoreListener(listener);
        dolphin.getModelStore().add(new ServerPresentationModel('p1', Collections.emptyList(), dolphin.getModelStore()));
        ServerPresentationModel modelWithType = new ServerPresentationModel('person1', Collections.emptyList(), dolphin.getModelStore());
        modelWithType.setPresentationModelType('person');
        dolphin.getModelStore().add(modelWithType);
        dolphin.getModelStore().add(new ServerPresentationModel('p2', Collections.emptyList(), dolphin.getModelStore()));
        dolphin.getModelStore().removeModelStoreListener('person', typedListener);
        dolphin.getModelStore().removeModelStoreListener(listener);
        Assert.assertEquals(3, listenerCallCount);
        Assert.assertEquals(1, typedListenerCallCount);
    }

    void testAddModelStoreListenerWithClosure() {
        int typedListenerCallCount = 0;
        int listenerCallCount = 0;
        ModelStoreListener listener = new ModelStoreListener() {
            @Override
            void modelStoreChanged(ModelStoreEvent event) {
                listenerCallCount++;
            }
        }
        ModelStoreListener typedListener = new ModelStoreListener() {
            @Override
            void modelStoreChanged(ModelStoreEvent event) {
                typedListenerCallCount++;
            }
        }
        dolphin.getModelStore().addModelStoreListener('person', typedListener);
        dolphin.getModelStore().addModelStoreListener(listener);
        dolphin.getModelStore().add(new ServerPresentationModel('p1', Collections.emptyList(), dolphin.getModelStore()));
        ServerPresentationModel modelWithType = new ServerPresentationModel('person1', Collections.emptyList(), dolphin.getModelStore());
        modelWithType.setPresentationModelType('person');
        dolphin.getModelStore().add(modelWithType);
        dolphin.getModelStore().add(new ServerPresentationModel('p2', Collections.emptyList(), dolphin.getModelStore()));
        Assert.assertEquals(3, listenerCallCount);
        Assert.assertEquals(1, typedListenerCallCount);
    }

    void testHasModelStoreListener() {
        ModelStoreListener listener = getListener();
        Assert.assertFalse(dolphin.getModelStore().hasModelStoreListener(null));
        Assert.assertFalse(dolphin.getModelStore().hasModelStoreListener(listener));
        dolphin.getModelStore().addModelStoreListener(listener);
        Assert.assertTrue(dolphin.getModelStore().hasModelStoreListener(listener));
        listener = getListener();
        dolphin.getModelStore().addModelStoreListener('person', listener)
        Assert.assertFalse(dolphin.getModelStore().hasModelStoreListener('car',listener));
        Assert.assertTrue(dolphin.getModelStore().hasModelStoreListener('person',listener));
    }

    void testRegisterDefaultActions() {
        dolphin.getServerConnector().registerDefaultActions();
        int numDefaultActions = dolphin.getServerConnector().getRegistrationCount();

        // multiple calls should not lead to multiple initializations
        dolphin.getServerConnector().registerDefaultActions();
        Assert.assertEquals(numDefaultActions, dolphin.getServerConnector().getRegistrationCount());
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
