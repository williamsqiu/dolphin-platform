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
package com.canoo.platform.server.client;

import com.canoo.platform.server.ServerListener;

/**
 * A listener interface to observe the lifecycle of a {@link ClientSession}. Each implemenetation of this
 * interface that is annotated by {@link ServerListener} will be created at runtime
 * and fired whenever a {@link ClientSession} has been created or before it will be destroyed.
 * As long as the underlying platform supports it (like JavaEE or Spring) CDI is supported in listener implementations.
 *
 * @author Hendrik Ebbers
 *
 * @see ServerListener
 * @see ClientSession
 */
public interface ClientSessionListener {

    /**
     * This method will be called whenever a new {@link ClientSession} has been created.
     * @param dolphinSession the dolphin session
     */
    void sessionCreated(ClientSession clientSession);

    /**
     * This method will be called whenever a {@link ClientSession} will be destroyed.
     * @param dolphinSession the dolphin session
     */
    void sessionDestroyed(ClientSession clientSession);

}
