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
package com.canoo.platform.core.functional;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.MAINTAINED;

/**
 * A basic function interface. Might be replaced in future with the Java 8 interface once all Dolphin Platform modules depend on Java8.
 *
 * @param <T> type of the input value
 * @param <R> type of the ouput value
 * @author Hendrik Ebbers
 */
@API(since = "0.x", status = MAINTAINED)
public interface Function<T, R> {

    /**
     * method of the function that will return a value based on the input value
     *
     * @param t input value
     * @return output value
     */
    R call(T t);
}