package com.canoo.platform.logger.client.util;

import com.canoo.platform.core.functional.Subscription;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by hendrikebbers on 29.12.17.
 */
public class BoundLogList<E> implements ObservableList<E>, Subscription {

    private final Subscription subscription;

    private final ObservableList<E> observableList;

    public BoundLogList(Subscription subscription, ObservableList<E> observableList) {
        this.subscription = subscription;
        this.observableList = observableList;
    }

    @Override
    public void unsubscribe() {
        subscription.unsubscribe();
    }

    @Override
    public void addListener(ListChangeListener<? super E> listener) {
        observableList.addListener(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super E> listener) {
        observableList.removeListener(listener);
    }

    @Override
    public boolean addAll(E[] elements) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public boolean setAll(E[] elements) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public boolean setAll(Collection<? extends E> col) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public boolean removeAll(E[] elements) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public boolean retainAll(E[] elements) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public void remove(int from, int to) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public int size() {
        return observableList.size();
    }

    @Override
    public boolean isEmpty() {
        return observableList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return observableList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return observableList.iterator();
    }

    @Override
    public Object[] toArray() {
        return observableList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return observableList.toArray(a);
    }

    @Override
    public boolean add(E e) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return observableList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public void clear() {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public E get(int index) {
        return observableList.get(index);
    }

    @Override
    public E set(int index, E element) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public void add(int index, E element) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public E remove(int index) {
        throw new RuntimeException("Can not mutate " + getClass());
    }

    @Override
    public int indexOf(Object o) {
        return observableList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return observableList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return observableList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return observableList.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return observableList.subList(fromIndex, toIndex);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        observableList.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        observableList.removeListener(listener);
    }
}
