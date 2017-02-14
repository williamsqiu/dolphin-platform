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
package org.opendolphin.core.client.comm
import groovy.util.logging.Log
import org.opendolphin.core.Attribute
import org.opendolphin.core.client.ClientAttribute
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientModelStore
import org.opendolphin.core.client.ClientPresentationModel
import org.opendolphin.core.comm.*

import java.beans.PropertyChangeEvent
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ClientConnectorTests extends GroovyTestCase {

	TestClientConnector clientConnector
	ClientDolphin dolphin
	AttributeChangeListener attributeChangeListener

	/**
	 * Since command transmission is done in parallel to test execution thread the test method might finish
	 * before the command processing is complete. Therefore {@link #tearDown()} waits for this CountDownLatch
	 * (which btw. is initialized in {@link #setUp()} and decremented in the handler of a {@code dolphin.sync()} call).
	 * Also putting asserts in the callback handler of a {@code dolphin.sync()} call seems not to be reliable since JUnit
	 * seems not to be informed (reliably) of failing assertions.
	 *
	 * Therefore the following approach for the test methods has been taken to:
	 * - initialize the CountDownLatch in {@code testBaseValueChange#setup()}
	 * - after the 'act' section of a test method: call {@code syncAndWaitUntilDone()} which releases the latch inside a dolphin.sync handler and then (in the main thread) waits for the latch
	 * - performs all assertions
	 */
	CountDownLatch syncDone


	@Override
	protected void setUp() {

		dolphin = new ClientDolphin()
		clientConnector = new TestClientConnector(dolphin, new Executor(){

			@Override
			void execute(Runnable command) {
				command.run();
			}
		});
		dolphin.clientConnector = clientConnector
		dolphin.clientModelStore = new ClientModelStore(dolphin)
		attributeChangeListener = dolphin.clientModelStore.@attributeChangeListener

		initLatch()
	}

	private void initLatch() {
		syncDone = new CountDownLatch(1)
	}

	private boolean waitForLatch() {
		return syncDone.await(2, TimeUnit.SECONDS)
	}

	void syncAndWaitUntilDone() {
		dolphin.sync {
			syncDone.countDown()
		}
		assert waitForLatch()
	}

	void assertCommandsTransmitted(int count) {
		assert clientConnector.getTransmitCount() == count
	}

	void assertOnlySyncCommandWasTransmitted() {
		assertCommandsTransmitted(1)
		// 1 command was sent because of the sent sync (resulting in a EMPTY command):
		assert clientConnector.transmittedCommands[0] instanceof EmptyNotification
	}

	void testSevereLogWhenCommandNotFound() {
		clientConnector.dispatchHandle( new Command() )
		syncAndWaitUntilDone()
		assertOnlySyncCommandWasTransmitted()
	}

	void testHandleSimpleCreatePresentationModelCommand() {
		final myPmId = "myPmId"
		assert null == dolphin.getPresentationModel(myPmId)
		CreatePresentationModelCommand command = new CreatePresentationModelCommand()
		command.pmId = myPmId
		clientConnector.dispatchHandle(command)
		assert dolphin.getPresentationModel(myPmId)
		syncAndWaitUntilDone()
		assertCommandsTransmitted(2)
	}

	//void testDefaultOnExceptionHandler() {
	//	clientConnector.uiThreadHandler = { it() } as UiThreadHandler
	//	String exceptionMessage = 'TestException thrown on purpose'
	//	def msg = shouldFail(RuntimeException) {
	//		clientConnector.getOnException().handle(new RuntimeException(exceptionMessage))
	//	}
	//	assert msg == exceptionMessage
	//}

	void testValueChange_OldAndNewValueSame() {
		attributeChangeListener.propertyChange(new PropertyChangeEvent("dummy", Attribute.VALUE_NAME, 'sameValue', 'sameValue'))
		syncAndWaitUntilDone()
		assertOnlySyncCommandWasTransmitted()
	}

	void testValueChange_noQualifier() {
		ClientAttribute attribute = new ClientAttribute('attr', 'initialValue')
		dolphin.clientModelStore.registerAttribute(attribute)
		attributeChangeListener.propertyChange(new PropertyChangeEvent(attribute, Attribute.VALUE_NAME, attribute.value, 'newValue'))
		syncAndWaitUntilDone()
		assertCommandsTransmitted(2)
		assert attribute.value == 'initialValue'
		assert clientConnector.transmittedCommands.any { it instanceof ValueChangedCommand }
	}

	void testValueChange_withQualifier() {
		syncDone = new CountDownLatch(1)

		ClientAttribute attribute = new ClientAttribute('attr', 'initialValue', 'qualifier')
		dolphin.clientModelStore.registerAttribute(attribute)
		attributeChangeListener.propertyChange(new PropertyChangeEvent(attribute, Attribute.VALUE_NAME, attribute.value, 'newValue'))
		syncAndWaitUntilDone()

		assertCommandsTransmitted(3)
		assert attribute.value == 'newValue'
		assert clientConnector.transmittedCommands.any { it instanceof ValueChangedCommand }
	}

	void testAddTwoAttributesInConstructorWithSameQualifierToSamePMIsNotAllowed() {
		shouldFail(IllegalStateException) {
			dolphin.presentationModel("1", new ClientAttribute("a", "0", "QUAL"), new ClientAttribute("b", "0", "QUAL"))
		}
	}

	void testMetaDataChange_UnregisteredAttribute() {
		ClientAttribute attribute = new ExtendedAttribute('attr', 'initialValue', 'qualifier')
		attribute.additionalParam = 'oldValue'
		attributeChangeListener.propertyChange(new PropertyChangeEvent(attribute, 'additionalParam', null, 'newTag'))
		syncAndWaitUntilDone()
		assertCommandsTransmitted(2)
		assert ChangeAttributeMetadataCommand == clientConnector.transmittedCommands[0].class
		assert 'oldValue' == attribute.additionalParam
	}

	void testHandle_ValueChanged_AttrNotExists() {
		assert !clientConnector.dispatchHandle(new ValueChangedCommand(attributeId: 0, oldValue: 'oldValue', newValue: 'newValue'))
	}

	void testHandle_ValueChangedWithBadBaseValueIsIgnored() {
		def attribute = new ClientAttribute('attr', 'initialValue')
		dolphin.clientModelStore.registerAttribute(attribute)
		clientConnector.dispatchHandle(new ValueChangedCommand(attributeId: attribute.id, oldValue: 'no-such-base-value', newValue: 'newValue'))
		assert 'initialValue' == attribute.value
	}

	void testHandle_ValueChangedWithBadBaseValueIgnoredInNonStrictMode() {
		clientConnector.strictMode = false
		def attribute = new ClientAttribute('attr', 'initialValue')
		dolphin.clientModelStore.registerAttribute(attribute)
		clientConnector.dispatchHandle(new ValueChangedCommand(attributeId: attribute.id, oldValue: 'no-such-base-value', newValue: 'newValue'))
		assert 'newValue' == attribute.value
		clientConnector.strictMode = true // re-setting for later tests
	}

	void testHandle_ValueChanged() {
		def attribute = new ClientAttribute('attr', 'initialValue')
		dolphin.clientModelStore.registerAttribute(attribute)
		assert !clientConnector.dispatchHandle(new ValueChangedCommand(attributeId: attribute.id, oldValue: 'initialValue', newValue: 'newValue'))
		assert 'newValue' == attribute.value
	}

	void testHandle_CreatePresentationModelTwiceFails() {
		clientConnector.dispatchHandle(new CreatePresentationModelCommand(pmId: 'p1', pmType: 'type', attributes: [[propertyName: 'attr', value: 'initialValue', qualifier: 'qualifier']]))
		def msg = shouldFail {
			clientConnector.dispatchHandle(new CreatePresentationModelCommand(pmId: 'p1', pmType: 'type', attributes: [[propertyName: 'attr', value: 'initialValue', qualifier: 'qualifier']]))
		}
		assert "There already is a presentation model with id 'p1' known to the client." == msg
	}

	void testHandle_CreatePresentationModel() {
		clientConnector.dispatchHandle(new CreatePresentationModelCommand(pmId: 'p1', pmType: 'type', attributes: [[propertyName: 'attr', value: 'initialValue', qualifier: 'qualifier']]))
		assert dolphin.getPresentationModel('p1')
		assert dolphin.getPresentationModel('p1').getAttribute('attr')
		assert 'initialValue' == dolphin.getPresentationModel('p1').getAttribute('attr').value
		assert 'qualifier' == dolphin.getPresentationModel('p1').getAttribute('attr').qualifier
		syncAndWaitUntilDone()
		assertCommandsTransmitted(2)
		assert CreatePresentationModelCommand == clientConnector.transmittedCommands[0].class
	}

	void testHandle_CreatePresentationModel_ClientSideOnly() {
		clientConnector.dispatchHandle(new CreatePresentationModelCommand(pmId: 'p1', pmType: 'type', clientSideOnly: true, attributes: [[propertyName: 'attr', value: 'initialValue', qualifier: 'qualifier']]))
		assert dolphin.getPresentationModel('p1')
		assert dolphin.getPresentationModel('p1').getAttribute('attr')
		assert 'initialValue' == dolphin.getPresentationModel('p1').getAttribute('attr').value
		assert 'qualifier' == dolphin.getPresentationModel('p1').getAttribute('attr').qualifier
		syncAndWaitUntilDone()
		assertOnlySyncCommandWasTransmitted()
	}

	void testHandle_CreatePresentationModel_MergeAttributesToExistingModel() {
		dolphin.presentationModel('p1')
		shouldFail(IllegalStateException) {
			clientConnector.dispatchHandle(new CreatePresentationModelCommand(pmId: 'p1', pmType: 'type', attributes: []))
		}
	}

	void testHandle_DeletePresentationModel() {
		ClientPresentationModel p1 = dolphin.presentationModel('p1')
		p1.clientSideOnly = true
		ClientPresentationModel p2 = dolphin.presentationModel('p2')
		clientConnector.dispatchHandle(new DeletePresentationModelCommand(pmId: null))
		def model = new ClientPresentationModel('p3', [])
		clientConnector.dispatchHandle(new DeletePresentationModelCommand(pmId: model.id))
		clientConnector.dispatchHandle(new DeletePresentationModelCommand(pmId: p1.id))
		clientConnector.dispatchHandle(new DeletePresentationModelCommand(pmId: p2.id))
		assert !dolphin.getPresentationModel(p1.id)
		assert !dolphin.getPresentationModel(p2.id)
		syncAndWaitUntilDone()
		// 3 commands will have been transferred:
		// 1: delete of p1 (causes no DeletedPresentationModelNotification since client side only)
		// 2: delete of p2
		// 3: DeletedPresentationModelNotification caused by delete of p2
		assertCommandsTransmitted(4)
		assert 1 == clientConnector.transmittedCommands.findAll { it instanceof DeletedPresentationModelNotification }.size()
	}

	@Log
	class TestClientConnector extends AbstractClientConnector {

		List<Command> transmittedCommands = []

		TestClientConnector(ClientDolphin clientDolphin, Executor executor) {
			super(clientDolphin, executor);
		}

		int getTransmitCount() {
			transmittedCommands.size()
		}

		List<Command> transmit(List<Command> commands) {
			println "transmit: ${commands.size()}"
			def result = new LinkedList<Command>()
			commands.each() { Command cmd ->
				result.addAll(transmitCommand(cmd))
			}
			result
		}

		List<Command> transmitCommand(Command command) {
			println "transmitCommand: $command"
			transmittedCommands << command
			return construct(command)
		}

		List construct(ChangeAttributeMetadataCommand command) {
			[new AttributeMetadataChangedCommand(attributeId: command.attributeId, metadataName: command.metadataName, value: command.value)]
		}

		List construct(Command command) {
			[]
		}

	}

	class ExtendedAttribute extends ClientAttribute {
		String additionalParam

		ExtendedAttribute(String propertyName, Object initialValue, String qualifier) {
			super(propertyName, initialValue, qualifier)
		}
	}

}
