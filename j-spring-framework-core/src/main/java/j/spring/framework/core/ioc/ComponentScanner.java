package j.spring.framework.core.ioc;

import j.spring.framework.core.web.annotation.*;
import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComponentScanner {

    private final Reflections reflections;

    public ComponentScanner(String basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    public Set<Class<?>> scan() {
        return Stream.of(Controller.class, Service.class, Repository.class, Component.class, Configuration.class)
                .flatMap(annotation -> reflections.getTypesAnnotatedWith(annotation).stream())
                .collect(Collectors.toSet());
    }

}
