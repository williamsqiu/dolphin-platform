/*
 * Copyright 2015-2016 Canoo Engineering AG.
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
package org.opendolphin.core.server.action

import org.opendolphin.core.comm.ChangeAttributeMetadataCommand
import org.opendolphin.core.server.DefaultServerDolphin
import org.opendolphin.core.server.ServerAttribute
import org.opendolphin.core.server.ServerDolphinFactory
import org.opendolphin.core.server.ServerPresentationModel
import org.opendolphin.core.server.comm.ActionRegistry

class StoreAttributeActionTests extends GroovyTestCase {
    DefaultServerDolphin dolphin
    ActionRegistry registry

    @Override
    protected void setUp() throws Exception {
        dolphin = ServerDolphinFactory.create()
        dolphin.serverModelStore.currentResponse = []
        registry = new ActionRegistry()
    }

    void testChangeAttributeMetadata() {
        new StoreAttributeAction(serverDolphin: dolphin).registerIn registry
        ServerAttribute attribute = new ServerAttribute('newAttribute', '')
        dolphin.addPresentationModel(new ServerPresentationModel('model', [attribute], dolphin.serverModelStore))
        registry.getAt('ChangeAttributeMetadata').first().handleCommand(new ChangeAttributeMetadataCommand(attributeId: attribute.id, metadataName: 'value', value: 'newValue'), [])
        assert 'newValue' == attribute.value
    }
}
