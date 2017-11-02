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
package com.canoo.dolphin.impl.collections;

import com.canoo.dp.impl.remoting.collections.ObservableArrayList;
import com.canoo.platform.remoting.ListChangeEvent;
import com.canoo.platform.remoting.ListChangeListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TestObservableArrayList {

    @Test
    public void testCreation() {
        ObservableArrayList<String> list = new ObservableArrayList<>();
        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);

        list = new ObservableArrayList<>(12);
        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);

        list = new ObservableArrayList<>("1", "2", "3");
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 3);

        list = new ObservableArrayList<>(Arrays.asList("1", "2", "3"));
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 3);
    }

    @Test
    public void testSize() {
        final ObservableArrayList<String> list = new ObservableArrayList<>();
        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);

        list.add("HUHU");
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 1);

        list.add("TEST");
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 2);

        list.clear();
        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);
    }

    @Test
    public void testAddAndRemove() {
        final ObservableArrayList<String> list = new ObservableArrayList<>();
        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);

        list.add("HUHU");
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 1);
        assertSameContent(list, Arrays.asList("HUHU"));
        list.clear();

        list.addAll("1", "2", "3");
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 3);
        assertSameContent(list, Arrays.asList("1", "2", "3"));
        list.clear();

        list.addAll(Arrays.asList("1", "2", "3"));
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 3);
        assertSameContent(list, Arrays.asList("1", "2", "3"));
        list.clear();

        list.addAll(Arrays.asList("1", "2", "3"));
        list.remove(0);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 2);
        assertSameContent(list, Arrays.asList("2", "3"));
        list.clear();

        list.addAll(Arrays.asList("1", "2", "3"));
        list.remove(1);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 2);
        assertSameContent(list, Arrays.asList("1", "3"));
        list.clear();

        list.addAll(Arrays.asList("1", "2", "3"));
        list.remove("2");
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 2);
        assertSameContent(list, Arrays.asList("1", "3"));
        list.clear();

        //Implementation still missing
//        list.addAll(Arrays.asList("1", "2", "3"));
//        list.removeAll("1", "2");
//        Assert.assertFalse(list.isEmpty());
//        Assert.assertEquals(list.size(), 1);
//        assertSameContent(list, Arrays.asList("3"));
//        list.clear();
//
//        list.addAll(Arrays.asList("1", "2", "3"));
//        list.removeAll(Arrays.asList("1", "3"));
//        Assert.assertFalse(list.isEmpty());
//        Assert.assertEquals(list.size(), 1);
//        assertSameContent(list, Arrays.asList("2"));
//        list.clear();
    }

    @Test
    public void testRemoveAll(){

        final AtomicBoolean removed = new AtomicBoolean(false);
        final AtomicBoolean added = new AtomicBoolean(false);
        final AtomicInteger callCount = new AtomicInteger(0);

        final ObservableArrayList<String> list = new ObservableArrayList<>();

        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);

        addOnChangeListener(removed, added, callCount, list);


        list.addAll("1", "2", "3", "4", "5", "6", "7" ,"8" , "9", "10");
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 10);
        Assert.assertTrue(added.get());
        Assert.assertFalse(removed.get());

        list.removeAll(Arrays.asList("1", "2", "3", "4", "5"));
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 5);
        assertSameContent(list, Arrays.asList("6", "7" ,"8" , "9", "10"));
        Assert.assertTrue(removed.get());
        Assert.assertFalse(added.get());
        Assert.assertEquals(2, callCount.get());
        list.clear();

    }

    private void addOnChangeListener(final AtomicBoolean removed, final AtomicBoolean added, final AtomicInteger callCount, ObservableArrayList<String> list) {
        list.onChanged(new ListChangeListener<String>() {
            @Override
            public void listChanged(ListChangeEvent<? extends String> evt) {
                callCount.incrementAndGet();
                if(evt.getChanges().iterator().next().isAdded()){
                    added.set(true);
                    removed.set(false);
                }else{
                    removed.set(true);
                    added.set(false);
                }
            }
        });
    }

    @Test
    public void testRetainAll(){

        final AtomicBoolean removed = new AtomicBoolean(false);
        final AtomicBoolean added = new AtomicBoolean(false);
        final AtomicInteger callCount = new AtomicInteger(0);

        final ObservableArrayList<String> list = new ObservableArrayList<>();
        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);

        addOnChangeListener(removed, added, callCount, list);

        list.addAll("1", "2", "3", "4", "5", "6", "7" ,"8" , "9", "10");
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 10);
        Assert.assertTrue(added.get());
        Assert.assertFalse(removed.get());

        list.retainAll(Arrays.asList("1", "2", "3", "4", "5"));
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 5);
        assertSameContent(list, Arrays.asList("1", "2", "3", "4", "5"));
        Assert.assertTrue(removed.get());
        Assert.assertFalse(added.get());

        Assert.assertEquals(2, callCount.get());
        list.clear();

        list.addAll("1", "2", "3", "4", "5", "6", "7" ,"8" , "9", "10");
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 10);

        list.retainAll("1", "2", "3", "4", "5");
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 5);
        assertSameContent(list, Arrays.asList("1", "2", "3", "4", "5"));
        list.clear();

    }

    @Test
    public void testRemoveRange() {
        final ObservableArrayList<String> list = new ObservableArrayList<>();
        
        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);
        
        list.addAll("1", "2", "3", "4", "5", "6", "7" ,"8" , "9", "10");
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 10);
        
        list.remove(1, 5);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 6);
        assertSameContent(list, Arrays.asList("1", "6", "7" ,"8" , "9", "10"));
        list.clear();
    }

    private <T> void assertSameContent(List<T> a, List<T> b) {
        Assert.assertTrue(a.size() == b.size());
        for(T t : a) {
            Assert.assertTrue(b.contains(t));
            Assert.assertTrue(a.indexOf(t) == b.indexOf(t));
        }
    }

}
