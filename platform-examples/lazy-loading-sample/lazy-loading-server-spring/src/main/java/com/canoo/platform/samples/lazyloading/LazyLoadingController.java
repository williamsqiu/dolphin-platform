package com.canoo.platform.samples.lazyloading;

import com.canoo.platform.remoting.BeanManager;
import com.canoo.platform.remoting.server.ClientSessionExecutor;
import com.canoo.platform.remoting.server.DolphinAction;
import com.canoo.platform.remoting.server.DolphinController;
import com.canoo.platform.remoting.server.DolphinModel;
import com.canoo.platform.remoting.server.RemotingContext;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static com.canoo.platform.samples.lazyloading.Constants.CONTROLLER_NAME;
import static com.canoo.platform.samples.lazyloading.Constants.REFRESH_ACTION;

@DolphinController(CONTROLLER_NAME)
public class LazyLoadingController {

    private final static ExecutorService taskExecutor = Executors.newCachedThreadPool();

    private final List<Future<?>> states = new ArrayList<>();

    private final ClientSessionExecutor sessionExecutor;

    private final BeanManager beanManager;

    @DolphinModel
    private LazyLoadingBean model;

    @Inject
    public LazyLoadingController(final RemotingContext remotingContext, final BeanManager beanManager) {
        this.sessionExecutor = remotingContext.createSessionExecutor();
        this.beanManager = beanManager;
    }

    @PostConstruct
    public void init() {
        refresh();
    }

    @DolphinAction(REFRESH_ACTION)
    public void refresh() {
        states.forEach(f -> f.cancel(true));
        states.clear();

        model.setLoading(true);
        model.getItems().clear();

        final int size = Optional.ofNullable(model.getRequestedSize()).orElse(10);
        IntStream.range(0, size).forEach(i -> {
            final Future<?> state = loadAsync("Item " + i);
            states.add(state);
        });

        final Future<?> stateCheck = checkSizeAsync(size);
        states.add(stateCheck);
    }

    private Future<?> checkSizeAsync(final int size) {
        return taskExecutor.submit(() -> {
            int loaded = 0;
            while (loaded < size) {
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                loaded = model.getItems().size();
            }
            sessionExecutor.runLaterInClientSession(() -> {
                model.setLoading(false);
            });
        });
    }

    private Future<?> loadAsync(final String name) {
        return taskExecutor.submit(() -> {
            try {
                Thread.sleep(new Random().nextInt(4_000));
                sessionExecutor.runLaterInClientSession(() -> {
                    addItem(name);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addItem(final String name) {
        final LazyLoadingItem item = beanManager.create(LazyLoadingItem.class);
        item.setName(name);
        item.setCreationDate(LocalDateTime.now());
        model.getItems().add(item);
    }
}
