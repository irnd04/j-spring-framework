package j.spring.framework.core.web.handlermapping.annotation.argresolver;

import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.handlermapping.annotation.RequestMappingMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Map;

public class RequestParameterHandlerMethodArgumentResolver extends AbstractHandlerMethodArgumentResolver {

    public RequestParameterHandlerMethodArgumentResolver(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public boolean supports(ParameterInfo parameterInfo) {
        return true;
    }

    @Override
    public Object resolve(ParameterInfo parameterInfo,
                          RequestMappingMethod requestMappingMethod, HttpServletRequest request) {


        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] parameters = parameterMap.get(parameterInfo.getName());
        if (parameters == null || parameters.length == 0) {
            return null;
        }

        Class<?> parameterType = parameterInfo.getType();
        if (parameterInfo.getType().isArray()) {
            parameterType = parameterInfo.getType().getComponentType();
        }


        Object result = Array.newInstance(parameterType, parameters.length);
        for (int i = 0; i < parameters.length; i++) {
            String p = parameters[i];
            Object value = converterManager.convert(p, parameterType);
            Array.set(result, i, value);
        }

        if (parameterInfo.getType().isArray()) {
            return result;
        }

        return Array.get(result, 0);
    }
}
