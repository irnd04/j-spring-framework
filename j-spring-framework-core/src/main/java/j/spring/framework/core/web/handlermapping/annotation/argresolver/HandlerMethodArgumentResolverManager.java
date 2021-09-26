package j.spring.framework.core.web.handlermapping.annotation.argresolver;

import com.google.common.collect.ImmutableList;
import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.converter.PrimitiveTypeUtils;
import j.spring.framework.core.web.handlermapping.annotation.RequestMappingMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class HandlerMethodArgumentResolverManager {

    private final List<HandlerMethodArgumentResolver> argumentResolvers;

    public HandlerMethodArgumentResolverManager(ApplicationContext applicationContext) {
        argumentResolvers = ImmutableList.of(
                new RequestBodyHandlerMethodArgumentResolver(applicationContext),
                new PathVariableHandlerMethodArgumentResolver(applicationContext),
                new RequestParameterHandlerMethodArgumentResolver(applicationContext));
    }

    private Object getParameterValue(RequestMappingMethod requestMappingMethod, HttpServletRequest request,
                                            ParameterInfo parameterInfo) throws IOException {
        for (HandlerMethodArgumentResolver resolver : argumentResolvers) {
            if (resolver.supports(parameterInfo)) {
                return resolver.resolve(parameterInfo, requestMappingMethod, request);
            }
        }

        return PrimitiveTypeUtils.defaultValue(parameterInfo.getType());
    }

    public Object[] getParameterValues(HttpServletRequest request, RequestMappingMethod requestMappingMethod) throws IOException {
        Method method = requestMappingMethod.getMethod();

        List<ParameterInfo> parameterInfos = ParameterInfo.getParameterInfos(method);
        Object[] parameterValues = new Object[parameterInfos.size()];

        for (int i = 0; i < parameterValues.length; i++) {
            ParameterInfo parameterInfo = parameterInfos.get(i);
            Object value = getParameterValue(requestMappingMethod, request, parameterInfo);
            parameterValues[i] = value;
        }

        return parameterValues;
    }
}
