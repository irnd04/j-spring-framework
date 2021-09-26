package j.spring.framework.core.ioc;

import j.spring.framework.core.exception.IocContainerErrorCode;
import j.spring.framework.core.exception.IocContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

public class CreateComponentUtils {

    private static final Logger logger = LoggerFactory.getLogger(CreateComponentUtils.class);

    public static Object newInstance(Class<?> clazz) {
        Objects.requireNonNull(clazz);
        if (clazz.isInterface()) {
            throw IocContainerException.of(IocContainerErrorCode.IOC_CONTAINER_INTERFACES_CANNOT_BE_CREATED, clazz.getName());
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO abstract class check
            throw IocContainerException
                    .of(IocContainerErrorCode.IOC_CONTAINER_NOT_FOUND_NO_ARG_CONSTRUCTOR, clazz.getName());
        }
    }

    public static <T> T newInstanceUsingConstructor(Constructor<T> ctor, Object... args) {
        Objects.requireNonNull(ctor);
        try {
            logger.debug(ctor.getName() + " " + Arrays.toString(args) + " create...");
            return ctor.newInstance(args);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw IocContainerException
                    .of(IocContainerErrorCode.IOC_CONTAINER_EXCEPTION, e);
        }
    }


}
