package com.canoo.dolphin.core;

import com.canoo.dp.impl.platform.core.Assert;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

/**
 * Created by hendrikebbers on 12.05.17.
 */
public class ClasspathScanner {

    private final Reflections reflections;

    public ClasspathScanner() {
        this(null);
    }

    public ClasspathScanner(final String rootPackage) {
        ConfigurationBuilder configuration = ConfigurationBuilder.build(ClasspathScanner.class.getClassLoader());

        if(rootPackage != null && !rootPackage.trim().isEmpty()) {
            configuration = configuration.forPackages(rootPackage);
            configuration = configuration.setUrls(ClasspathHelper.forPackage(rootPackage));
            configuration = configuration.filterInputsBy(new FilterBuilder().includePackage(rootPackage));
        }

        //Special case for JBOSS Application server to get all classes
        try {
            Enumeration<URL> res = ClasspathScanner.class.getClassLoader().getResources("");
            configuration.getUrls().addAll(Collections.list(res));
        } catch (IOException e) {
            throw new RuntimeException("Error in Dolphin Platform controller class scan", e);
        }


        //Remove native libs (will be added on Mac in a Spring Boot app)
        Set<URL> urls = configuration.getUrls();
        List<URL> toRemove = new ArrayList<>();
        for (URL url : urls) {
            if (url.toString().endsWith(".jnilib")) {
                toRemove.add(url);
            }
        }
        for (URL url : toRemove) {
            configuration.getUrls().remove(url);
        }

        reflections = new Reflections(configuration);
    }

    /**
     * Returns a set that contains all classes in the classpath that are annotated with the given annotation
     * @param annotation the annotation
     * @return the set of annotated classes
     */
    public synchronized Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation) {
        Assert.requireNonNull(annotation, "annotation");
        return reflections.getTypesAnnotatedWith(annotation);
    }
}
