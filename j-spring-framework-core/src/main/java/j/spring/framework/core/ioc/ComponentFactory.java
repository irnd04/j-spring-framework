package j.spring.framework.core.ioc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import j.spring.framework.core.exception.IocContainerErrorCode;
import j.spring.framework.core.exception.IocContainerException;
import j.spring.framework.core.web.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ComponentFactory implements ApplicationContext {

    private final Map<Class<?>, Generator> generators;
    private final Map<Class<?>, Object> components = Maps.newHashMap();
    private final CreateComponentCycleDetector cycleDetector =
            new CreateComponentCycleDetector();

    public ComponentFactory(Set<Class<?>> components) {
        this.generators = components
                .stream()
                .collect(Collectors.toMap(component -> component,
                        AnnotatedComponentGenerator::new));
        initialize();
        beanInitialize();
    }

    private void beanInitialize() {
        List<Object> configs = values().stream()
                .filter(x -> x.getClass().isAnnotationPresent(Configuration.class))
                .collect(Collectors.toList());
        for (Object config : configs) {
            BeanAnnotationFinder.getMethods(config.getClass())
                    .forEach(method ->
                            generators.put(method.getReturnType(), new AnnotatedBeanGenerator(config, method)));
        }
        initialize();
    }

    private void initialize() {
        getCandidates().forEach(candidate -> {
            Object component = newComponent(candidate);
            InjectorManager.inject(component, this);
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Class<T> clazz) {
        Class<?> implClass = findImplClass(clazz);
        T r = (T) components.get(implClass);
        if (r == null) {
            throw IocContainerException.of(IocContainerErrorCode.IOC_CONTAINER_NOT_FOUND, clazz.getName());
        }
        return r;
    }

    public boolean contains(Class<?> clazz) {
        Set<Class<?>> implClasses = findImplClasses(clazz);
        if (implClasses.size() > 1) {
            throw IocContainerException.of(
                    IocContainerErrorCode.IOC_CONTAINER_CANNOT_CHOOSE_INJECTION_OBJECT, clazz.getName());
        }

        if (implClasses.isEmpty()) {
            return false;
        }
        return components.get(implClasses.iterator().next()) != null;
    }

    @Override
    public Collection<Object> values() {
        return components.values();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> find(Class<T> clazz) {
        return components.values()
                .stream()
                .filter(o -> clazz.isAssignableFrom(o.getClass()))
                .map(o -> (T) o)
                .collect(Collectors.toList());
    }

    @Override
    public <T> T findOne(Class<T> clazz) {
        List<T> finds = find(clazz);
        if (finds.isEmpty()) {
            return null;
        }
        if (finds.size() > 1) {
            throw IocContainerException.of(IocContainerErrorCode.IOC_CONTAINER_NUMBER_OF_RESULT_ZERO_OR_ONE,
                    clazz.getName(), String.valueOf(finds.size()));
        }
        return finds.get(0);
    }

    public Set<Class<?>> getCandidates() {
        return generators.keySet();
    }

    public Generator getGenerator(Class<?> clazz) {
        return generators.get(clazz);
    }

    private boolean isNotCandidate(Class<?> clazz) {
        return !getCandidates().contains(clazz);
    }

    public Object newComponent(Class<?> clazz) {
        Class<?> validClass = convertValidClass(clazz);

        Object component = components.get(validClass);
        if (component != null) {
            return component;
        }

        Generator generator = getGenerator(validClass);
        Class<?>[] argTypes = generator.getArgTypes();

        if (argTypes.length == 0) {
            component = generator.create();
            add(validClass, component);
            return component;
        }

        component = newComponentWithArgs(generator, argTypes);
        add(validClass, component);
        return component;
    }

    private Object newComponentWithArgs(Generator generator, Class<?>[] argTypes) {
        cycleDetector.detect(generator.getType());
        List<Object> args = Lists.newArrayList();
        for (Class<?> argClass : argTypes) {
            Class<?> validClass = convertValidClass(argClass);
            Object component = components.get(validClass);
            if (component == null) {
                component = newComponent(validClass);
            }
            args.add(component);
        }
        return generator.create(args.toArray());
    }

    private Class<?> convertValidClass(Class<?> clazz) {
        Class<?> implClass = findImplClass(clazz);
        if (isNotCandidate(implClass)) {
            throw IocContainerException.of(IocContainerErrorCode.IOC_CONTAINER_NOT_FOUND, clazz.getName());
        }
        return implClass;
    }

    private Set<Class<?>> findImplClasses(Class<?> clazz) {
        if (!clazz.isInterface()) {
            return Sets.newHashSet(clazz);
        }

        Set<Class<?>> finds = Sets.newHashSet();
        for (Class<?> c : getCandidates()) {
            if (clazz.isAssignableFrom(c)) {
                finds.add(c);
            }
        }

        return finds;
    }

    private Class<?> findImplClass(Class<?> clazz) {
        Set<Class<?>> implClasses = findImplClasses(clazz);

        if (implClasses.size() == 1) {
            return implClasses.iterator().next();
        }

        if (implClasses.isEmpty()) {
            throw IocContainerException.of(IocContainerErrorCode.IOC_CONTAINER_NOT_FOUND, clazz.getName());
        }

        throw IocContainerException.of(
                IocContainerErrorCode.IOC_CONTAINER_CANNOT_CHOOSE_INJECTION_OBJECT, clazz.getName());
    }

    private void add(Class<?> clazz, Object component) {
        components.put(clazz, component);
    }

}
