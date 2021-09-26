package j.spring.framework.core.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class AnnotatedComponentGenerator extends AbstractGenerator {

    private static final Logger logger = LoggerFactory.getLogger(AnnotatedComponentGenerator.class);

    public AnnotatedComponentGenerator(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Class<?>[] getArgTypes() {
        Constructor<?> constructor = AutowireAnnotationFinder.getConstructor(getType());
        if (constructor == null) {
            return new Class[] {};
        }
        return constructor.getParameterTypes();
    }

    @Override
    public Object create(Object... args) {
        logger.debug(getType().getName() + " " + Arrays.toString(args) + " create...");
        if (args.length == 0) {
            return CreateComponentUtils.newInstance(getType());
        }
        Constructor<?> constructor = AutowireAnnotationFinder.getConstructor(getType());
        return CreateComponentUtils.newInstanceUsingConstructor(constructor, args);
    }

}
