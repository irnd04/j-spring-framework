package j.spring.framework.core.ioc;

import j.spring.framework.core.exception.IocContainerErrorCode;
import j.spring.framework.core.exception.IocContainerException;
import j.spring.framework.core.web.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import static org.reflections.ReflectionUtils.*;

public class AutowireAnnotationFinder {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Constructor<?> getConstructor(Class<?> clazz) {
        Set<Constructor> constructors = getAllConstructors(clazz, withAnnotation(Autowired.class));
        if (constructors.isEmpty()) {
            return null;
        }

        if (constructors.size() > 1) {
            throw IocContainerException.of(
                    IocContainerErrorCode.IOC_CONTAINER_AUTOWIRE_ONLY_ONE_CONSTRUCTOR_CAN_BE_USED, clazz.getName());
        }

        return constructors.iterator().next();
    }

    @SuppressWarnings("unchecked")
    public static Set<Method> getSetterMethods(Object o) {
        return getAllMethods(o.getClass(), withAnnotation(Autowired.class), withReturnType(void.class));
    }

    @SuppressWarnings("unchecked")
    public static Set<Field> getFields(Object o) {
        return getAllFields(o.getClass(), withAnnotation(Autowired.class));
    }

}
