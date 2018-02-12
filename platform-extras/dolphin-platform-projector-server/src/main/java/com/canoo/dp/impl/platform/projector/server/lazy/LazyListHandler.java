package com.canoo.dp.impl.platform.projector.server.lazy;

import com.canoo.dp.impl.platform.projector.lazy.LazyList;
import com.canoo.dp.impl.platform.projector.lazy.LazyListElement;
import com.canoo.platform.core.functional.Subscription;

import java.util.function.IntFunction;

public class LazyListHandler<E extends LazyListElement> {

    private LazyList<E> listModel;

    private Subscription neededContentSubscription;

    private IntFunction<E> elementFactory;

    public LazyListHandler(LazyList<E> listModel) {
        setListModel(listModel);
    }

    public LazyList<E> getListModel() {
        return listModel;
    }

    public void setListModel(LazyList<E> listModel) {
        this.listModel = listModel;

        if(neededContentSubscription != null) {
            neededContentSubscription.unsubscribe();
        }

        neededContentSubscription = listModel.getNeededContent().onChanged(e -> {
            e.getChanges().forEach(c -> {
                if(c.isAdded()) {
                    for (int i = c.getFrom(); i < c.getTo(); i++) {
                        E elementBean = elementFactory.apply(listModel.getNeededContent().get(i));
                        elementBean.indexProperty().set(listModel.getNeededContent().get(i));
                        listModel.getLoadedContent().add(elementBean);
                    }
                }
            });
        });
    }

    public IntFunction<E> getElementFactory() {
        return elementFactory;
    }

    public void setElementFactory(IntFunction<E> elementFactory) {
        this.elementFactory = elementFactory;
    }

    public void setListLength(int listLength) {
        listModel.listLengthProperty().set(listLength);
    }

    public int getListLength() {
        return listModel.listLengthProperty().get();
    }
}
