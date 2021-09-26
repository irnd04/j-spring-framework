package j.spring.framework.core.web.handlermapping.annotation.argresolver;

import com.google.common.base.CharMatcher;
import j.spring.framework.core.PathMatcher;
import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;
import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.annotation.PathVariable;
import j.spring.framework.core.web.handlermapping.annotation.RequestMappingMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class PathVariableHandlerMethodArgumentResolver extends AbstractHandlerMethodArgumentResolver {

    private final PathMatcher pathMatcher = new PathMatcher();


    public PathVariableHandlerMethodArgumentResolver(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public boolean supports(ParameterInfo parameterInfo) {
        return parameterInfo.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolve(ParameterInfo parameterInfo,
                          RequestMappingMethod requestMappingMethod, HttpServletRequest request) {
        String mappingUrl = requestMappingMethod.getMappingUrl();

        String url = getValidUrl(request);

        Map<String, String> pathVariableMap =
                pathMatcher.extractUriTemplateVariables(mappingUrl, url);

        String result = pathVariableMap.get(parameterInfo.getName());
        if (result == null) {
            throw WebServerException
                    .of(WebServerErrorCode.PATH_VARIABLE_NOT_FOUND, requestMappingMethod.getMethod().getName());
        }

        return converterManager.convert(result, parameterInfo.getType());
    }

    private String getValidUrl(HttpServletRequest request) {
        String url = request.getRequestURI();
        url = CharMatcher.is('/').trimTrailingFrom(url);
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        return url;
    }

}
