package j.spring.framework.core.web.handlermapping.annotation.argresolver;

import com.google.common.collect.Lists;
import j.spring.framework.core.ParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class ParameterInfo {

    private Parameter parameter;
    private String name;

    private static final ParameterNameDiscoverer discoverer =
            new ParameterNameDiscoverer();

    private ParameterInfo() {

    }

    private ParameterInfo(String name, Parameter parameter) {
        this.parameter = parameter;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return parameter.getType();
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return parameter.getAnnotation(annotationClass);
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return parameter.isAnnotationPresent(annotationClass);
    }

    public static List<ParameterInfo> getParameterInfos(Method method) {

        List<ParameterInfo> result = Lists.newArrayList();

        String[] parameterNames = discoverer.getParameterNames(method);
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            result.add(new ParameterInfo(parameterNames[i], parameters[i]));
        }

        return result;
    }

}
