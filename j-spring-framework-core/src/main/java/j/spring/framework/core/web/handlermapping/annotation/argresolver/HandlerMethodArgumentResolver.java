package j.spring.framework.core.web.handlermapping.annotation.argresolver;

import j.spring.framework.core.web.handlermapping.annotation.RequestMappingMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface HandlerMethodArgumentResolver {

    boolean supports(ParameterInfo parameterInfo);

    Object resolve(ParameterInfo parameterInfo,
                   RequestMappingMethod requestMappingMethod, HttpServletRequest request) throws IOException;

}
