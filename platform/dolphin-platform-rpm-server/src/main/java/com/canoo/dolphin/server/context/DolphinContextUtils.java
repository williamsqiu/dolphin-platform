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
package com.canoo.dolphin.server.context;

import com.canoo.dolphin.util.Assert;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.Future;

public class DolphinContextUtils {

    private static final ThreadLocal<DolphinContext> currentContext = new ThreadLocal<>();

    private static final HashMap<String, WeakReference<DolphinContext>> weakContextMap = new HashMap<>();

    private DolphinContextUtils() {
    }

    public static Future<Void> runLaterInClientSession(String clientSessionId, Runnable runnable) {
        Assert.requireNonBlank(clientSessionId, "clientSessionId");
        Assert.requireNonNull(runnable, "runnable");

        WeakReference<DolphinContext> ref = weakContextMap.get(clientSessionId);
        DolphinContext dolphinContext = ref.get();
        Assert.requireNonNull(dolphinContext, "dolphinContext");

        return dolphinContext.runLater(runnable);
    }
}
