package j.spring.framework.core.web.handlermapping.annotation.argresolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.annotation.RequestBody;
import j.spring.framework.core.web.handlermapping.annotation.RequestMappingMethod;
import j.spring.framework.core.web.util.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestBodyHandlerMethodArgumentResolver extends AbstractHandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;

    public RequestBodyHandlerMethodArgumentResolver(ApplicationContext applicationContext) {
        super(applicationContext);
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public boolean supports(ParameterInfo parameterInfo) {
        return parameterInfo.isAnnotationPresent(RequestBody.class);
    }

    @Override
    public Object resolve(ParameterInfo parameterInfo, RequestMappingMethod requestMappingMethod, HttpServletRequest request) throws IOException {
        String json = RequestUtils.readBody(request);
        return objectMapper.readValue(json, parameterInfo.getType());
    }
}
