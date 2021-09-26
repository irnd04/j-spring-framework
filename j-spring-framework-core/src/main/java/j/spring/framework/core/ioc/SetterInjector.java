package j.spring.framework.core.ioc;

import j.spring.framework.core.exception.IocContainerErrorCode;
import j.spring.framework.core.exception.IocContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class SetterInjector implements Injector {

    private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);

    @Override
    public void inject(Object o, ComponentFactory factory) {
        Objects.requireNonNull(o);
        Set<Method> setterMethods = AutowireAnnotationFinder.getSetterMethods(o);
        for (Method setter : setterMethods) {
            logger.debug(o.getClass().getName() + "#" + setter.getName(), " setter inject.");
            Object[] parameters = Stream.of(setter.getParameterTypes())
                    .map(p -> {
                        if (factory.contains(p)) {
                            return factory.get(p);
                        }
                        return factory.newComponent(p);
                    })
                    .toArray();
            try {
                setter.invoke(o, parameters);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw IocContainerException.of(IocContainerErrorCode.IOC_CONTAINER_EXCEPTION, e);
            }
        }
    }
}
