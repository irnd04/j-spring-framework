package j.spring.framework.core.ioc;

import j.spring.framework.core.exception.IocContainerErrorCode;
import j.spring.framework.core.exception.IocContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

public class FieldInjector implements Injector {

    private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);

    @Override
    public void inject(Object o, ComponentFactory factory) {
        Objects.requireNonNull(o);
        Set<Field> fields = AutowireAnnotationFinder.getFields(o);
        for (Field field : fields) {
            logger.debug(o.getClass().getName() + " field inject ");
            Class<?> fieldType = field.getType();
            Object value = null;
            if (factory.contains(fieldType)) {
                value = factory.get(fieldType);
            } else {
                value = factory.newComponent(fieldType);
            }
            try {
                field.setAccessible(true);
                field.set(o, value);
            } catch (Exception e) {
                throw IocContainerException.of(IocContainerErrorCode.IOC_CONTAINER_EXCEPTION, e);
            }
        }
    }

}
