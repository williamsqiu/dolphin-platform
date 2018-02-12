package com.canoo.dp.impl.platform.projector.metadata;

import com.canoo.dp.impl.platform.projector.base.WithLayoutMetadata;
import com.canoo.dp.impl.platform.projector.metadata.concrete.BooleanKeyValueBean;
import com.canoo.dp.impl.platform.projector.metadata.concrete.DoubleKeyValueBean;
import com.canoo.dp.impl.platform.projector.metadata.concrete.StringKeyValueBean;
import com.canoo.platform.core.functional.Subscription;
import com.canoo.platform.remoting.BeanManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MetadataUtilities {

    public static Subscription addListenerToMetadata(WithLayoutMetadata metadata, Runnable onChange) {
        final Map<KeyValue, List<Subscription>> subscriptionMap = new HashMap<>();

        Subscription mainSubscription = metadata.getLayoutMetadata().onChanged(e -> {
            e.getChanges().forEach(c -> {
                if(c.isRemoved()) {
                    c.getRemovedElements().forEach(m -> {
                        List<Subscription> subscriptions = subscriptionMap.computeIfAbsent(m, key -> new ArrayList<>());
                        subscriptions.forEach(s -> s.unsubscribe());
                        subscriptions.clear();
                    });
                }
                if(c.isAdded()) {
                    metadata.getLayoutMetadata().subList(c.getFrom(), c.getTo()).forEach(m -> {
                        List<Subscription> subscriptions = subscriptionMap.computeIfAbsent(m, key -> new ArrayList<>());
                        subscriptions.add(m.keyProperty().onChanged(event -> onChange.run()));
                        subscriptions.add(m.valueProperty().onChanged(event -> onChange.run()));
                    });
                }
            });
        });
        metadata.getLayoutMetadata().forEach(m -> {
            List<Subscription> subscriptions = subscriptionMap.computeIfAbsent(m, key -> new ArrayList<>());
            subscriptions.add(m.keyProperty().onChanged(event -> onChange.run()));
            subscriptions.add(m.valueProperty().onChanged(event -> onChange.run()));
        });

        return () -> {
            mainSubscription.unsubscribe();
            subscriptionMap.forEach((k, v) -> {
                v.forEach(s -> s.unsubscribe());
            });
        };
    }

    public static KeyValue<String> getOrCreateStringBasedMetadata(final String metadataKey, final WithLayoutMetadata bean, final BeanManager beanManager) {
        return getOrCreateMetadata(StringKeyValueBean.class, metadataKey, bean, beanManager);
    }

    public static KeyValue<Boolean> getOrCreateBooleanBasedMetadata(final String metadataKey, final WithLayoutMetadata bean, final BeanManager beanManager) {
        return getOrCreateMetadata(BooleanKeyValueBean.class, metadataKey, bean, beanManager);
    }

    public static KeyValue<Double> getOrCreateDoubleBasedMetadata(final String metadataKey, final WithLayoutMetadata bean, final BeanManager beanManager) {
        return getOrCreateMetadata(DoubleKeyValueBean.class, metadataKey, bean, beanManager);
    }

    private static <T> KeyValue<T> getOrCreateMetadata(Class<? extends KeyValue> beanClass, final String metadataKey, final WithLayoutMetadata bean, final BeanManager beanManager) {
        KeyValue<T> metadata = getMetadata(metadataKey, bean).orElse(null);
        if (metadata == null) {
            metadata = beanManager.create(beanClass);
            metadata.setKey(metadataKey);
            bean.getLayoutMetadata().add(metadata);
        }
        return metadata;
    }

    public static Optional<KeyValue> getMetadata(final String metadataKey, final WithLayoutMetadata bean) {
        return bean.getLayoutMetadata().stream().
                filter(m -> Optional.ofNullable(m.getKey()).orElse("").equals(metadataKey)).
                findAny();
    }
}
