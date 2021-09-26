package j.spring.framework.core.ioc;

import j.spring.framework.core.web.annotation.Bean;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllMethods;

public class BeanAnnotationFinder {

    @SuppressWarnings("unchecked")
    public static Set<Method> getMethods(Class<?> clazz) {
        return getAllMethods(clazz, ReflectionUtils.withAnnotation(Bean.class));
    }

}
