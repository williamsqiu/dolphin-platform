/*
 * Copyright 2015-2018 Canoo Engineering AG.
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
package com.canoo.dolphin.webdeployment;

/**
 * This class defines some constanst that are shared between Java clients and the server. Currently the client access
 * for controllers and controller action is based on Strings.
 */
public class Constants {

    /**
     * Defines the unique name of the controller type that is used in this example
     */
    public final static String CONTROLLER_NAME = "MyController";

    /**
     * Defines the name of the reset action in a MyController instance that can be triggered from the client
     */
    public final static String RESET_ACTION = "reset";

}
