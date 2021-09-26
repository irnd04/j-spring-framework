package j.spring.framework.core.web.handlermapping.annotation;

import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.handlermapping.HandlerAdapter;
import j.spring.framework.core.web.handlermapping.annotation.argresolver.HandlerMethodArgumentResolverManager;
import j.spring.framework.core.web.view.JsonView;
import j.spring.framework.core.web.view.View;
import j.spring.framework.core.web.view.ViewRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RequestMappingHandlerAdapter implements HandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(RequestMappingHandlerAdapter.class);

    private final HandlerMethodArgumentResolverManager argumentResolverManager;

    public RequestMappingHandlerAdapter(ApplicationContext applicationContext) {
        this.argumentResolverManager = new HandlerMethodArgumentResolverManager(applicationContext);
    }

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof RequestMappingMethod);
    }

    @Override
    public View handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestMappingMethod requestMappingMethod = (RequestMappingMethod) handler;

        Object instance = requestMappingMethod.getInstance();
        Method method = requestMappingMethod.getMethod();

        Object[] parameterValues =
                argumentResolverManager.getParameterValues(request, requestMappingMethod);

        logger.debug(instance.getClass().getName() + "#" + method.getName() + " " + Arrays.toString(parameterValues) + " method invoke.");
        Object result;
        try {
            result = method.invoke(instance, parameterValues);
        } catch (InvocationTargetException e) {
            throw (Exception) e.getTargetException();
        }

        if (requestMappingMethod.hasResponseBody()) {
            return new JsonView(result, response);
        }

        return new ViewRenderer();
    }
}
