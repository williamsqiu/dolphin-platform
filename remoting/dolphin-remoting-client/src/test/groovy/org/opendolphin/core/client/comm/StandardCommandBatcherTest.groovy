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

import org.junit.Assert

public class StandardCommandBatcherTest extends GroovyTestCase {

    private CommandBatcher batcher

    @Override
    protected void setUp() throws Exception {
        batcher = new CommandBatcher();
    }

    public void testEmpty() {
        Assert.assertTrue(batcher.isEmpty());
    }

    public void testOne() {
        CommandAndHandler cah = new CommandAndHandler(null);
        batcher.batch(cah);
        //TODO: How to convert this to Java?
        Assert.assertEquals(Collections.singletonList(cah), batcher.getWaitingBatches().getVal());
    }

    public void testMultipleDoesNotBatch() {

        List<CommandAndHandler> list = Arrays.asList(new CommandAndHandler(null), new CommandAndHandler(null), new CommandAndHandler(null));
        for(CommandAndHandler cwh : list) {
            batcher.batch(cwh);
        }

        Assert.assertEquals(Collections.singletonList(list.get(0)), batcher.getWaitingBatches().getVal());
        Assert.assertEquals(Collections.singletonList(list.get(1)), batcher.getWaitingBatches().getVal());
        Assert.assertEquals(Collections.singletonList(list.get(2)), batcher.getWaitingBatches().getVal());
    }
}
