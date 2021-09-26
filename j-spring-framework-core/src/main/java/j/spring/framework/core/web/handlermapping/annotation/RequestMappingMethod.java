package j.spring.framework.core.web.handlermapping.annotation;

import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;
import j.spring.framework.core.web.annotation.RequestMapping;
import j.spring.framework.core.web.annotation.RequestMethod;
import j.spring.framework.core.web.annotation.ResponseBody;
import j.spring.framework.core.web.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class RequestMappingMethod {

    private final Object instance;
    private final Method method;
    private final String mappingUrl;
    private final List<RequestMethod> requestMethods;

    private RequestMappingMethod(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
        RequestMapping requestMappingMethod = method.getAnnotation(RequestMapping.class);
        if (requestMappingMethod == null) {
            throw WebServerException
                    .of(WebServerErrorCode.METHOD_MUST_HAVE_REQUEST_MAPPING_ANNOTATION, method.getName());
        }
        RequestMapping requestMappingController = instance.getClass().getAnnotation(RequestMapping.class);
        String mappingUrl = requestMappingMethod.value();
        if (requestMappingController != null) {
            mappingUrl = StringUtils.urlJoin(requestMappingController.value(), mappingUrl);
        }
        this.mappingUrl = mappingUrl;
        this.requestMethods = Arrays.asList(requestMappingMethod.method());
    }

    public static RequestMappingMethod of(Object instance, Method method) {
        return new RequestMappingMethod(instance, method);
    }

    public String getMappingUrl() {
        return mappingUrl;
    }

    public boolean containsRequestMethod(RequestMethod requestMethod) {
        return requestMethods.contains(requestMethod);
    }

    public List<RequestMethod> getRequestMethods() {
        return requestMethods;
    }

    public Object getInstance() {
        return instance;
    }

    public Method getMethod() {
        return method;
    }

    public boolean hasResponseBody() {
        return instance.getClass().getAnnotation(ResponseBody.class) != null
                || method.getAnnotation(ResponseBody.class) != null;
    }
}
