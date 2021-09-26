package j.spring.framework.core.web.handlermapping.annotation;

import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.annotation.Controller;
import j.spring.framework.core.web.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withAnnotation;

public class RequestMappingAnnotationScanner {

    private final ApplicationContext applicationContext;

    public RequestMappingAnnotationScanner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<RequestMappingMethod> scan() {
        List<Object> controllers = applicationContext.values()
                .stream()
                .filter(instance -> instance.getClass().isAnnotationPresent(Controller.class))
                .collect(Collectors.toList());

        List<RequestMappingMethod> requestMappingMethods = new ArrayList<>();
        for (Object ctrl : controllers) {
            @SuppressWarnings("unchecked")
            Set<Method> methods = getAllMethods(ctrl.getClass(), withAnnotation(RequestMapping.class));
            for (Method method : methods) {
                requestMappingMethods.add(RequestMappingMethod.of(ctrl, method));
            }
        }
        return requestMappingMethods;
    }

}
