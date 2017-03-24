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
package org.opendolphin.core.server.comm;

import org.opendolphin.core.comm.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionRegistry {

    private final Map<Class<? extends Command>, List<CommandHandler>> actions = new HashMap();

    public Map<Class<? extends Command>, List<CommandHandler>> getActions() {
        return Collections.unmodifiableMap(actions);
    }

    public void register(Class commandClass, CommandHandler serverCommand) {
        List<CommandHandler> actions = getActionsFor(commandClass);
        if (!actions.contains(serverCommand)) {
            actions.add(serverCommand);
        }
    }

    public List<CommandHandler> getActionsFor(Class<? extends Command> commandClass) {
        List<CommandHandler> actions = this.actions.get(commandClass);
        if (actions == null) {
            actions = new ArrayList<CommandHandler>();
            this.actions.put(commandClass, actions);
        }

        return actions;
    }
}
