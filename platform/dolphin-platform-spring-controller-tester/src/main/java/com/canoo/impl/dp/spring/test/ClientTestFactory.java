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
package com.canoo.impl.dp.spring.test;

import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.platform.remoting.client.ControllerProxy;
import com.canoo.platform.remoting.client.Param;
import com.canoo.platform.spring.test.AsyncCondition;
import com.canoo.platform.spring.test.ControllerTestException;
import com.canoo.platform.spring.test.ControllerUnderTest;
import org.apiguardian.api.API;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class ClientTestFactory {

    public static <T> ControllerUnderTest<T> createController(final TestClientContext clientContext, final String controllerName) {
        Assert.requireNonNull(clientContext, "clientContext");
        Assert.requireNonBlank(controllerName, "controllerName");
        try {
            final ControllerProxy<T> proxy = (ControllerProxy<T>) clientContext.createController(controllerName).get();
            return new ControllerUnderTest<T>() {
                @Override
                public T getModel() {
                    return proxy.getModel();
                }

                @Override
                public void invoke(String actionName, Param... params) {
                    try {
                        proxy.invoke(actionName, params).get();
                    } catch (Exception e) {
                        throw new ControllerTestException("Error in action invocation", e);
                    }
                }

                @Override
                public AsyncCondition createAsyncCondition() {
                    return new AsyncCondition() {

                        private final AtomicBoolean ping = new AtomicBoolean();

                        private void sendPing() {
                            clientContext.sendPing();
                        }

                        @Override
                        public void await() throws InterruptedException {
                            ping.set(true);
                            while (ping.get()) {
                                sendPing();

                                Thread.sleep(100);
                            }
                        }

                        @Override
                        public void await(long time, TimeUnit unit) throws InterruptedException, TimeoutException {
                            long endTime = System.currentTimeMillis() + unit.toMillis(time);
                            ping.set(true);
                            while (ping.get() && System.currentTimeMillis() < endTime) {
                                sendPing();

                                Thread.sleep(100);
                            }
                            if(ping.get()) {
                                throw new TimeoutException("Timeout!");
                            }
                        }

                        @Override
                        public void signal() {
                            ping.set(false);
                        }
                    };
                }

                @Override
                public void destroy() {
                    try {
                        proxy.destroy().get();
                    } catch (Exception e) {
                        throw new ControllerTestException("Error in destroy", e);
                    }
                }
            };
        } catch (Exception e) {
            throw new ControllerTestException("Can't create controller proxy", e);
        }
    }
}
