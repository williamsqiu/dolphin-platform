package org.opendolphin.core.server.comm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendolphin.core.comm.Command;

import java.util.List;

public class ActionRegistryTests {
    @Before
    public void setUp() throws Exception {
        registry = new ActionRegistry();
    }

    @Test
    public void testRegisterCommand() {
        //given:
        Assert.assertEquals(0, registry.getActions().size());
        CommandHandler<TestDataCommand> firstAction = new CommandHandler<TestDataCommand>() {
            @Override
            public void handleCommand(TestDataCommand command, List response) {

            }

        };

        //when:
        registry.register(TestDataCommand.class, firstAction);

        //then:
        Assert.assertEquals(1, registry.getActionsFor(TestDataCommand.class).size());
        Assert.assertTrue(registry.getActionsFor(TestDataCommand.class).contains(firstAction));
        Assert.assertEquals(1, registry.getActions().size());
    }

    @Test
    public void testRegisterCommandHandler() {

        //given:
        CommandHandler<TestDataCommand> commandHandler = new CommandHandler<TestDataCommand>() {
            @Override
            public void handleCommand(TestDataCommand command, List response) {

            }

        };

        //when:
        registry.register(TestDataCommand.class, commandHandler);

        //then:
        Assert.assertTrue(registry.getActionsFor(TestDataCommand.class).contains(commandHandler));
        Assert.assertEquals(1, registry.getActions().size());
        Assert.assertEquals(1, registry.getActionsFor(TestDataCommand.class).size());
    }

    @Test
    public void testRegisterCommand_MultipleCalls() {

        //given:
        Assert.assertEquals(0, registry.getActions().size());
        CommandHandler<TestDataCommand> action = new CommandHandler<TestDataCommand>() {
            @Override
            public void handleCommand(TestDataCommand command, List response) {

            }

        };

        //when:
        registry.register(TestDataCommand.class, action);

        //then:
        Assert.assertEquals(1, registry.getActions().size());
        Assert.assertEquals(1, registry.getActionsFor(TestDataCommand.class).size());

        //when:
        registry.register(TestDataCommand.class, action);

        //then:
        Assert.assertEquals(1, registry.getActionsFor(TestDataCommand.class).size());
    }

    private ActionRegistry registry;

    final private class TestDataCommand extends Command {
    }
}
