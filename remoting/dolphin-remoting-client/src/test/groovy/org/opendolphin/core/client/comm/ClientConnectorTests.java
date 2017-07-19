package org.opendolphin.core.client.comm;

import com.canoo.dolphin.impl.commands.InterruptLongPollCommand;
import com.canoo.dolphin.impl.commands.StartLongPollCommand;
import groovy.lang.Closure;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendolphin.core.Attribute;
import org.opendolphin.core.client.ClientAttribute;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientModelStore;
import org.opendolphin.core.client.ClientPresentationModel;
import org.opendolphin.core.client.DefaultModelSynchronizer;
import org.opendolphin.core.client.ModelSynchronizer;
import org.opendolphin.core.comm.AttributeMetadataChangedCommand;
import org.opendolphin.core.comm.ChangeAttributeMetadataCommand;
import org.opendolphin.core.comm.Command;
import org.opendolphin.core.comm.CreatePresentationModelCommand;
import org.opendolphin.core.comm.DeletePresentationModelCommand;
import org.opendolphin.core.comm.DeletedPresentationModelNotification;
import org.opendolphin.core.comm.EmptyNotification;
import org.opendolphin.core.comm.ValueChangedCommand;
import org.opendolphin.util.DirectExecutor;
import org.opendolphin.util.Provider;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientConnectorTests {

    @Before
    public void setUp() {
        dolphin = new ClientDolphin();
        ModelSynchronizer defaultModelSynchronizer = new DefaultModelSynchronizer(new Provider<AbstractClientConnector>() {
            @Override
            public AbstractClientConnector get() {
                return dolphin.getClientConnector();
            }

        });
        ClientModelStore clientModelStore = new ClientModelStore(defaultModelSynchronizer);
        dolphin.setClientModelStore(clientModelStore);
        clientConnector = new TestClientConnector(clientModelStore, DirectExecutor.getInstance());
        dolphin.setClientConnector(clientConnector);

        try {
            attributeChangeListener = dolphin.getModelStore().getAttributeChangeListener();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        clientConnector.connect(false);

        initLatch();
    }

    private void initLatch() {
        syncDone = new CountDownLatch(1);
    }

    private boolean waitForLatch() {
        try {
            return syncDone.await(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void syncAndWaitUntilDone() {
        dolphin.sync(new Closure<Object>(this, this) {
            public void doCall(Object it) {
                syncDone.countDown();
            }

            public void doCall() {
                doCall(null);
            }

        });
        Assert.assertTrue(waitForLatch());
    }

    private void assertCommandsTransmitted(final int count) {
        Assert.assertEquals(count, clientConnector.getTransmitCount());
    }

    private void assertOnlySyncCommandWasTransmitted() {
        assertCommandsTransmitted(1);
        // 1 command was sent because of the sent sync (resulting in a EMPTY command):
        Assert.assertFalse(clientConnector.getTransmittedCommands().isEmpty());
        Assert.assertEquals(EmptyNotification.class, clientConnector.getTransmittedCommands().get(0).getClass());
    }

    @Test
    public void testSevereLogWhenCommandNotFound() {
        clientConnector.dispatchHandle(new EmptyNotification());
        syncAndWaitUntilDone();
        assertOnlySyncCommandWasTransmitted();
    }

    @Test
    public void testHandleSimpleCreatePresentationModelCommand() {
        final String myPmId = "myPmId";
        Assert.assertEquals(null, dolphin.getModelStore().findPresentationModelById(myPmId));
        CreatePresentationModelCommand command = new CreatePresentationModelCommand();
        command.setPmId(myPmId);
        clientConnector.dispatchHandle(command);
        Assert.assertNotNull(dolphin.getModelStore().findPresentationModelById(myPmId));
        syncAndWaitUntilDone();
        assertCommandsTransmitted(2);
    }

    @Test
    public void testValueChange_OldAndNewValueSame() {
        attributeChangeListener.propertyChange(new PropertyChangeEvent("dummy", Attribute.VALUE_NAME, "sameValue", "sameValue"));
        syncAndWaitUntilDone();
        assertOnlySyncCommandWasTransmitted();
    }

    @Test
    public void testValueChange_noQualifier() {
        ClientAttribute attribute = new ClientAttribute("attr", "initialValue");
        dolphin.getModelStore().registerAttribute(attribute);
        attributeChangeListener.propertyChange(new PropertyChangeEvent(attribute, Attribute.VALUE_NAME, attribute.getValue(), "newValue"));
        syncAndWaitUntilDone();
        assertCommandsTransmitted(2);
        Assert.assertEquals("initialValue", attribute.getValue());

        boolean valueChangedCommandFound = false;
        for (Command c : clientConnector.getTransmittedCommands()) {
            if (c instanceof ValueChangedCommand) {
            }

            valueChangedCommandFound = true;
        }

        Assert.assertTrue(valueChangedCommandFound);
    }

    @Test
    public void testValueChange_withQualifier() {
        syncDone = new CountDownLatch(1);
        ClientAttribute attribute = new ClientAttribute("attr", "initialValue", "qualifier");
        dolphin.getModelStore().registerAttribute(attribute);
        attributeChangeListener.propertyChange(new PropertyChangeEvent(attribute, Attribute.VALUE_NAME, attribute.getValue(), "newValue"));
        syncAndWaitUntilDone();
        assertCommandsTransmitted(3);
        Assert.assertEquals("newValue", attribute.getValue());

        boolean valueChangedCommandFound = false;
        for (Command c : clientConnector.getTransmittedCommands()) {
            if (c instanceof ValueChangedCommand) {
            }

            valueChangedCommandFound = true;
        }

        Assert.assertTrue(valueChangedCommandFound);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddTwoAttributesInConstructorWithSameQualifierToSamePMIsNotAllowed() {
        dolphin.getModelStore().createModel("1", null, new ClientAttribute("a", "0", "QUAL"), new ClientAttribute("b", "0", "QUAL"));
    }

    @Test
    public void testMetaDataChange_UnregisteredAttribute() {
        ClientAttribute attribute = new ExtendedAttribute("attr", "initialValue", "qualifier");
        ((ExtendedAttribute) attribute).setAdditionalParam("oldValue");
        attributeChangeListener.propertyChange(new PropertyChangeEvent(attribute, "additionalParam", null, "newTag"));
        syncAndWaitUntilDone();
        assertCommandsTransmitted(2);
        Assert.assertFalse(clientConnector.getTransmittedCommands().isEmpty());
        Assert.assertEquals(ChangeAttributeMetadataCommand.class, clientConnector.getTransmittedCommands().get(0).getClass());
        Assert.assertEquals("oldValue", ((ExtendedAttribute) attribute).getAdditionalParam());
    }

    @Test
    public void testHandle_ValueChangedWithBadBaseValueIgnoredInNonStrictMode() {
        ClientAttribute attribute = new ClientAttribute("attr", "initialValue");
        dolphin.getModelStore().registerAttribute(attribute);
        clientConnector.dispatchHandle(new ValueChangedCommand(attribute.getId(), "newValue"));
        Assert.assertEquals("newValue", attribute.getValue());
    }

    @Test
    public void testHandle_ValueChanged() {
        ClientAttribute attribute = new ClientAttribute("attr", "initialValue");
        dolphin.getModelStore().registerAttribute(attribute);

        clientConnector.dispatchHandle(new ValueChangedCommand(attribute.getId(), "newValue"));
        Assert.assertEquals("newValue", attribute.getValue());
    }

    @Test(expected = Exception.class)
    public void testHandle_CreatePresentationModelTwiceFails() {
        List<Map<String, Object>> attributes = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("propertyName", "attr");
        map.put("value", "initialValue");
        map.put("qualifier", "qualifier");
        ((ArrayList<Map<String, Object>>) attributes).add(map);
        clientConnector.dispatchHandle(new CreatePresentationModelCommand("p1", "type", attributes));
        clientConnector.dispatchHandle(new CreatePresentationModelCommand("p1", "type", attributes));
    }

    @Test
    public void testHandle_CreatePresentationModel() {
        List<Map<String, Object>> attributes = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<>();
        map.put("propertyName", "attr");
        map.put("value", "initialValue");
        map.put("qualifier", "qualifier");
        ((ArrayList<Map<String, Object>>) attributes).add(map);

        clientConnector.dispatchHandle(new CreatePresentationModelCommand("p1", "type", attributes));
        Assert.assertNotNull(dolphin.getModelStore().findPresentationModelById("p1"));
        Assert.assertNotNull(dolphin.getModelStore().findPresentationModelById("p1").getAttribute("attr"));
        Assert.assertEquals("initialValue", dolphin.getModelStore().findPresentationModelById("p1").getAttribute("attr").getValue());
        Assert.assertEquals("qualifier", dolphin.getModelStore().findPresentationModelById("p1").getAttribute("attr").getQualifier());
        syncAndWaitUntilDone();
        assertCommandsTransmitted(2);
        Assert.assertFalse(clientConnector.getTransmittedCommands().isEmpty());
        Assert.assertEquals(CreatePresentationModelCommand.class, clientConnector.getTransmittedCommands().get(0).getClass());
    }

    @Test
    public void testHandle_CreatePresentationModel_ClientSideOnly() {
        List<Map<String, Object>> attributes = new ArrayList<Map<String, Object>>();
        Map map = new HashMap();
        map.put("propertyName", "attr");
        map.put("value", "initialValue");
        map.put("qualifier", "qualifier");
        ((ArrayList<Map<String, Object>>) attributes).add(map);
        clientConnector.dispatchHandle(new CreatePresentationModelCommand("p1", "type", attributes, true));
        Assert.assertNotNull(dolphin.getModelStore().findPresentationModelById("p1"));
        Assert.assertNotNull(dolphin.getModelStore().findPresentationModelById("p1").getAttribute("attr"));
        Assert.assertEquals("initialValue", dolphin.getModelStore().findPresentationModelById("p1").getAttribute("attr").getValue());
        Assert.assertEquals("qualifier", dolphin.getModelStore().findPresentationModelById("p1").getAttribute("attr").getQualifier());
        syncAndWaitUntilDone();
        assertOnlySyncCommandWasTransmitted();
    }

    @Test(expected = IllegalStateException.class)
    public void testHandle_CreatePresentationModel_MergeAttributesToExistingModel() {
        dolphin.getModelStore().createModel("p1", null);
        clientConnector.dispatchHandle(new CreatePresentationModelCommand("p1", "type", Collections.emptyList()));
    }

    @Test
    public void testHandle_DeletePresentationModel() {
        ClientPresentationModel p1 = dolphin.getModelStore().createModel("p1", null);
        p1.setClientSideOnly(true);
        ClientPresentationModel p2 = dolphin.getModelStore().createModel("p2", null);
        clientConnector.dispatchHandle(new DeletePresentationModelCommand(null));
        ClientPresentationModel model = new ClientPresentationModel("p3", Collections.emptyList());
        clientConnector.dispatchHandle(new DeletePresentationModelCommand(model.getId()));
        clientConnector.dispatchHandle(new DeletePresentationModelCommand(p1.getId()));
        clientConnector.dispatchHandle(new DeletePresentationModelCommand(p2.getId()));
        Assert.assertNull(dolphin.getModelStore().findPresentationModelById(p1.getId()));
        Assert.assertNull(dolphin.getModelStore().findPresentationModelById(p2.getId()));
        syncAndWaitUntilDone();
        // 3 commands will have been transferred:
        // 1: delete of p1 (causes no DeletedPresentationModelNotification since client side only)
        // 2: delete of p2
        // 3: DeletedPresentationModelNotification caused by delete of p2
        assertCommandsTransmitted(4);

        int deletedPresentationModelNotificationCount = 0;
        for (Command c : clientConnector.getTransmittedCommands()) {
            if (c instanceof DeletedPresentationModelNotification) {
                deletedPresentationModelNotificationCount = deletedPresentationModelNotificationCount + 1;
            }

        }
        Assert.assertEquals(1, deletedPresentationModelNotificationCount);
    }

    private TestClientConnector clientConnector;
    private ClientDolphin dolphin;
    private AttributeChangeListener attributeChangeListener;
    /**
     * Since command transmission is done in parallel to test execution thread the test method might finish
     * before the command processing is complete. Therefore tearDown() waits for this CountDownLatch
     * (which btw. is initialized in {@link #setUp()} and decremented in the handler of a {@code dolphin.sync ( )} call).
     * Also putting asserts in the callback handler of a {@code dolphin.sync ( )} call seems not to be reliable since JUnit
     * seems not to be informed (reliably) of failing assertions.
     * <p>
     * Therefore the following approach for the test methods has been taken to:
     * - initialize the CountDownLatch in {@code testBaseValueChange # setup ( )}
     * - after the "act" section of a test method: call {@code syncAndWaitUntilDone ( )} which releases the latch inside a dolphin.sync handler and then (in the main thread) waits for the latch
     * - performs all assertions
     */
    private CountDownLatch syncDone;

    public class TestClientConnector extends AbstractClientConnector {
        public TestClientConnector(ClientModelStore modelStore, Executor uiExecutor) {
            super(modelStore, uiExecutor, new CommandBatcher(), new SimpleExceptionHandler(), Executors.newCachedThreadPool());
        }

        public int getTransmitCount() {
            return transmittedCommands.size();
        }

        public List<Command> transmit(List<Command> commands) {
            System.out.print("transmit: " + commands.size());
            LinkedList result = new LinkedList<Command>();
            for (Command cmd : commands) {
                result.addAll(transmitCommand(cmd));
            }

            return result;
        }

        @Override
        public String getClientId() {
            return null;
        }

        public List<Command> transmitCommand(Command command) {
            System.out.print("transmitCommand: " + command);

            if (command != null && !(command instanceof StartLongPollCommand) && !(command instanceof InterruptLongPollCommand)) {
                DefaultGroovyMethods.leftShift(transmittedCommands, command);
            }

            return construct(command);
        }

        public List<Command> getTransmittedCommands() {
            return transmittedCommands;
        }

        public List<AttributeMetadataChangedCommand> construct(ChangeAttributeMetadataCommand command) {
            return Collections.singletonList(new AttributeMetadataChangedCommand(command.getAttributeId(), command.getMetadataName(), command.getValue()));
        }

        public List construct(Command command) {
            return Collections.emptyList();
        }

        private List<Command> transmittedCommands = new ArrayList<Command>();
    }

    public class ExtendedAttribute extends ClientAttribute {
        public ExtendedAttribute(String propertyName, Object initialValue, String qualifier) {
            super(propertyName, initialValue, qualifier);
        }

        public String getAdditionalParam() {
            return additionalParam;
        }

        public void setAdditionalParam(String additionalParam) {
            this.additionalParam = additionalParam;
        }

        private String additionalParam;
    }
}
