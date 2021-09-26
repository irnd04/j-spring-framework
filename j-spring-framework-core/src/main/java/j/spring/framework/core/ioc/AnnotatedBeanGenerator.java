package j.spring.framework.core.ioc;

import j.spring.framework.core.exception.IocContainerErrorCode;
import j.spring.framework.core.exception.IocContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotatedBeanGenerator extends AbstractGenerator {

    private final static Logger logger = LoggerFactory.getLogger(AnnotatedBeanGenerator.class);

    private final Object config;
    private final Method method;

    public AnnotatedBeanGenerator(Object config, Method method) {
        super(method.getReturnType());
        this.config = config;
        this.method = method;
    }

    @Override
    public Class<?>[] getArgTypes() {
        return method.getParameterTypes();
    }

    @Override
    public Object create(Object... args) {
        try {
            logger.debug(config.getClass().getName() + "#" + method.getName()
                    + " " + Arrays.toString(args) + " bean create..");
            return method.invoke(config, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw IocContainerException.of(IocContainerErrorCode.IOC_CONTAINER_EXCEPTION, e);
        }
    }
}
