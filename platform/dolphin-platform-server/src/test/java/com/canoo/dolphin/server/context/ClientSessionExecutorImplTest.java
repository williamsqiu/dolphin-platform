package com.canoo.dolphin.server.context;

import com.canoo.dolphin.server.ClientSessionExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.Executors;

/**
 * Created by hendrikebbers on 02.03.17.
 */
public class ClientSessionExecutorImplTest {

    @Test
    public void testCreation() {
        try {
            ClientSessionExecutor executor = new ClientSessionExecutorImpl(Executors.newSingleThreadExecutor());
        } catch (Exception e) {
            Assert.fail("Can not create executor", e);
        }
    }

    @Test
    public void testInvalidCreation() {
        try {
            ClientSessionExecutor executor = new ClientSessionExecutorImpl(null);
            Assert.fail("Creating executor with null value should not be possible!");
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void testPassingTask() {
        //given:
        ClientSessionExecutor executor = new ClientSessionExecutorImpl(Executors.newSingleThreadExecutor());

        //then:
        executor.runLaterInClientSession(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Test
    public void testPassingInvalidTask() {
        //given:
        ClientSessionExecutor executor = new ClientSessionExecutorImpl(Executors.newSingleThreadExecutor());

        //then:
        try {
            executor.runLaterInClientSession(null);
            Assert.fail("Passing a null value should not be possible!");
        } catch (NullPointerException e) {

        }
    }

}
