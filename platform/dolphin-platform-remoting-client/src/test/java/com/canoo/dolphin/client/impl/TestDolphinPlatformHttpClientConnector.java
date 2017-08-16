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
package com.canoo.dolphin.client.impl;

import com.canoo.dolphin.client.DummyUiThreadHandler;
import com.canoo.dp.impl.client.DolphinPlatformHttpClientConnector;
import com.canoo.dp.impl.remoting.commands.CreateContextCommand;
import com.canoo.dp.impl.platform.client.ClientSessionSupportImpl;
import com.canoo.platform.client.HttpURLConnectionFactory;
import com.canoo.platform.remoting.client.ClientConfiguration;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientModelStore;
import org.opendolphin.core.client.DefaultModelSynchronizer;
import org.opendolphin.core.client.comm.AbstractClientConnector;
import org.opendolphin.core.client.comm.SimpleExceptionHandler;
import org.opendolphin.core.comm.Command;
import org.opendolphin.core.comm.JsonCodec;
import org.opendolphin.util.DolphinRemotingException;
import org.opendolphin.util.Provider;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestDolphinPlatformHttpClientConnector {


    @Test(expectedExceptions = DolphinRemotingException.class)
    public void testBadResponse() throws DolphinRemotingException {

        ClientConfiguration clientConfiguration = new ClientConfiguration(getDummyURL(), new DummyUiThreadHandler());
        clientConfiguration.setConnectionFactory(new HttpURLConnectionFactory() {
            @Override
            public HttpURLConnection create(URL url) throws IOException {
                return new HttpURLConnection(url) {
                    @Override
                    public void disconnect() {

                    }

                    @Override
                    public boolean usingProxy() {
                        return false;
                    }

                    @Override
                    public void connect() throws IOException {

                    }

                    @Override
                    public OutputStream getOutputStream() throws IOException {
                        return new ByteArrayOutputStream();
                    }

                };
            }
        });

        ClientDolphin clientDolphin = new ClientDolphin();
        clientDolphin.setClientModelStore(new ClientModelStore(new DefaultModelSynchronizer(new Provider<AbstractClientConnector>() {
            @Override
            public AbstractClientConnector get() {
                return null;
            }
        })));
        DolphinPlatformHttpClientConnector connector = new DolphinPlatformHttpClientConnector(clientConfiguration, clientDolphin.getModelStore(), new JsonCodec(), new SimpleExceptionHandler(), new ClientSessionSupportImpl());

        List<Command> commands = new ArrayList<>();
        commands.add(new CreateContextCommand());
        connector.transmit(commands);
    }

    private URL getDummyURL() {
        try {
            return new URL("http://dummyURL");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Exception occurred while creating URL", e);
        }
    }
}
