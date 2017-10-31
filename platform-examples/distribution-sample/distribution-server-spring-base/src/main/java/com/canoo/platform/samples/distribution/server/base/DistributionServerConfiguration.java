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
package com.canoo.platform.samples.distribution.server.base;

import com.canoo.platform.remoting.server.event.DolphinEventBus;
import com.canoo.platform.remoting.server.spring.EnableRemoting;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableRemoting
public class DistributionServerConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DistributionItemStore createStore(final DolphinEventBus eventBus) {
        DistributionItemStore store = new DistributionItemStore();
        eventBus.subscribe(DistributionEventTopics.ITEM_ADDED, e -> store.addItem(e.getData()));
        eventBus.subscribe(DistributionEventTopics.ITEM_REMOVED, e -> store.removeItem(e.getData()));
        eventBus.subscribe(DistributionEventTopics.ITEM_MARK_CHANGED, e -> store.changeItemState(e.getData()));
        return store;
    }
}
