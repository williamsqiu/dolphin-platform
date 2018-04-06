package com.canoo.impl.platform.core.context;

import com.canoo.dp.impl.platform.core.context.ContextManagerImpl;
import com.canoo.platform.core.context.Context;
import com.canoo.platform.core.context.ContextManager;
import com.canoo.platform.core.functional.Subscription;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;

public class ContextManagerImplTests {

    @Test
    public void testGlobalContextDefaults() {
        //given:
        final ContextManager manager = new ContextManagerImpl();


        //then:
        Assert.assertEquals(manager.getGlobalContexts().size(), 5);
        checkForGlobalContext(manager, "hostName");
        checkForGlobalContext(manager, "platform.version");
        checkForGlobalContext(manager, "canonicalHostName");
        checkForGlobalContext(manager, "hostAddress");
        checkForGlobalContext(manager, "application.name");
    }

    @Test
    public void testGlobalContext() {
        //given:
        final ContextManager manager = new ContextManagerImpl();

        //when:
        manager.addGlobalContext("KEY", "VALUE");

        //then:
        Assert.assertEquals(manager.getGlobalContexts().size(), 6);
        checkForGlobalContext(manager, "KEY", "VALUE");
    }

    @Test
    public void testOverrideGlobalContext() {
        //given:
        final ContextManager manager = new ContextManagerImpl();

        //when:
        manager.addGlobalContext("KEY", "VALUE");
        manager.addGlobalContext("KEY", "VALUE-2");

        //then:
        Assert.assertEquals(manager.getGlobalContexts().size(), 6);
        checkForGlobalContext(manager, "KEY", "VALUE-2");
    }

    @Test
    public void testRemoveGlobalContext() {
        //given:
        final ContextManager manager = new ContextManagerImpl();

        //when:
        final Subscription subscription = manager.addGlobalContext("KEY", "VALUE");
        subscription.unsubscribe();

        //then:
        Assert.assertEquals(manager.getGlobalContexts().size(), 5);
        checkForGlobalContextMissing(manager, "KEY");
    }

    private void checkForGlobalContextMissing(final ContextManager manager, final String type) {
        final Optional<Context> context = manager.getGlobalContexts()
                .stream()
                .filter(c -> c.getType().equals(type))
                .findAny();
        Assert.assertFalse(context.isPresent(), "Context of type " + type + " was found.");
    }

    private void checkForGlobalContext(final ContextManager manager, final String type) {
        final Optional<Context> context = manager.getGlobalContexts()
                .stream()
                .filter(c -> c.getType().equals(type))
                .findAny();
        Assert.assertTrue(context.isPresent(), "Context of type " + type + " not found.");
    }

    private void checkForGlobalContext(final ContextManager manager, final String type, final String value) {
        final Optional<Context> context = manager.getGlobalContexts()
                .stream()
                .filter(c -> c.getType().equals(type))
                .filter(c -> c.getValue().equals(value))
                .findAny();
        Assert.assertTrue(context.isPresent(), "Context of type " + type + " and value " + value + " not found.");
    }

}
