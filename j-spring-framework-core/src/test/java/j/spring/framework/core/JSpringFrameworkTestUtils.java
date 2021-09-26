package j.spring.framework.core;

import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.ioc.ComponentFactory;
import j.spring.framework.core.ioc.ComponentScanner;
import j.spring.framework.core.mock.MockHttpServletRequest;
import j.spring.framework.core.web.annotation.RequestMethod;
import j.spring.framework.core.web.converter.ConverterManager;
import j.spring.framework.core.web.exceptionhandler.ExceptionHandlerManager;
import j.spring.framework.core.web.handlermapping.HandlerExecutionChain;
import j.spring.framework.core.web.handlermapping.HandlerMapping;
import j.spring.framework.core.web.handlermapping.annotation.*;
import j.spring.framework.core.web.handlermapping.annotation.argresolver.HandlerMethodArgumentResolverManager;

public class JSpringFrameworkTestUtils {

    public static ApplicationContext getApplicationContext(String basePackage) {
        ComponentScanner scanner = new ComponentScanner(basePackage);
        return new ComponentFactory(scanner.scan());
    }

    public static ConverterManager getConverterManager(String basePackage) {
        return new ConverterManager(getApplicationContext(basePackage));
    }

    public static RequestMappingHandlerMapping getRequestMappingHandlerMapping(String basePackage) {
        ApplicationContext applicationContext = getApplicationContext(basePackage);
        return new RequestMappingHandlerMapping(applicationContext);
    }

    public static RequestMappingHandlerAdapter getRequestMappingHandlerAdapter(String basePackage) {
        ApplicationContext applicationContext = getApplicationContext(basePackage);
        return new RequestMappingHandlerAdapter(applicationContext);
    }


    public static ExceptionHandlerManager getExceptionHandlerManager(String basePackage) {
        return new ExceptionHandlerManager(getApplicationContext(basePackage));
    }

    public static HandlerMethodArgumentResolverManager getHandlerMethodArgumentResolverManager(String basePackage) {
        return new HandlerMethodArgumentResolverManager(getApplicationContext(basePackage));
    }

    public static MockHttpServletRequest newMockHttpServletRequest(RequestMethod method, String url) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod(method.name());
        request.setRequestURI(url);
        return request;
    }

    public static RequestMappingMethod getRequestMappingMethod(
            HandlerMapping handlerMapping,
            RequestMethod method, String url) throws Exception {
        HandlerExecutionChain handlerExecutionChain =
                handlerMapping.getHandler(JSpringFrameworkTestUtils.newMockHttpServletRequest(method, url));
        return handlerExecutionChain == null ? null : (RequestMappingMethod) handlerExecutionChain.getHandler();
    }

}
