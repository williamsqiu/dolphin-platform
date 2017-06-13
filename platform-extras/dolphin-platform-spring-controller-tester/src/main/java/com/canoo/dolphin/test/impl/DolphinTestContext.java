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

import com.canoo.dolphin.server.config.RemotingConfiguration;
import com.canoo.dolphin.server.context.DolphinContext;
import com.canoo.dolphin.server.controller.ControllerRepository;
import com.canoo.platform.core.functional.Callback;
import com.canoo.impl.server.beans.ManagedBeanFactory;
import com.canoo.impl.server.client.ClientSessionImpl;
import com.canoo.impl.server.client.ClientSessionProvider;
import org.opendolphin.core.comm.Command;

import java.util.List;

public class DolphinTestContext extends DolphinContext {

    public DolphinTestContext(RemotingConfiguration configuration, ClientSessionProvider dolphinSessionProvider, ManagedBeanFactory managedBeanFactory, ControllerRepository controllerRepository) {
        super(configuration, new ClientSessionImpl("Test-123"), dolphinSessionProvider, managedBeanFactory, controllerRepository, createEmptyCallback());
    }

    private static Callback<DolphinContext> createEmptyCallback() {
        return new Callback<DolphinContext>() {
            @Override
            public void call(DolphinContext context) {

            }
        };
    }

    @Override
    public List<Command> handle(List<Command> commands) {
        return super.handle(commands);
    }
}
