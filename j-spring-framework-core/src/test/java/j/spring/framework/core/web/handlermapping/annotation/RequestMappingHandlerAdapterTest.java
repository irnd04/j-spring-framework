package j.spring.framework.core.web.handlermapping.annotation;

import j.spring.framework.core.JSpringFrameworkTestUtils;
import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;
import j.spring.framework.core.mock.MockHttpServletRequest;
import j.spring.framework.core.mock.MockHttpServletResponse;
import j.spring.framework.core.testdata.web.exceptionhandler.ExceptionHandler1;
import j.spring.framework.core.testdata.web.exceptionhandler.ExceptionHandler2;
import j.spring.framework.core.testdata.web.handlermapping.annotation.methodhandler.controller.ControllerRequestMappingFinderTestController;
import j.spring.framework.core.testdata.web.handlermapping.annotation.resolver.pathvariable.PathVariableController;
import j.spring.framework.core.testdata.web.interceptor.InterceptorController;
import j.spring.framework.core.testdata.web.interceptor.Test1Interceptor;
import j.spring.framework.core.testdata.web.interceptor.Test2Interceptor;
import j.spring.framework.core.testdata.web.interceptor.Test3Interceptor;
import j.spring.framework.core.web.annotation.RequestMethod;
import j.spring.framework.core.web.exceptionhandler.DefaultExceptionHandler;
import j.spring.framework.core.web.exceptionhandler.ExceptionHandlerManager;
import j.spring.framework.core.web.handlermapping.HandlerAdapter;
import j.spring.framework.core.web.handlermapping.HandlerExecutionChain;
import j.spring.framework.core.web.handlermapping.HandlerMapping;
import j.spring.framework.core.web.view.JsonView;
import j.spring.framework.core.web.view.View;
import j.spring.framework.core.web.view.ViewRenderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestMappingHandlerAdapterTest {

    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    @DisplayName("ViewRenderer를 반환한다.")
    void viewRendererTest() throws Exception {
        RequestMethod method = RequestMethod.GET;
        String url = "/test";
        String basePackage = PathVariableController.class.getPackage().getName();
        HandlerMapping handlerMapping = JSpringFrameworkTestUtils
                .getRequestMappingHandlerMapping(basePackage);
        HandlerAdapter handlerAdapter =
                JSpringFrameworkTestUtils.getRequestMappingHandlerAdapter(basePackage);
        MockHttpServletRequest mockHttpServletRequest =
                JSpringFrameworkTestUtils.newMockHttpServletRequest(method, url);
        HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(mockHttpServletRequest);
        View view = handlerAdapter.handle(mockHttpServletRequest, response, handlerExecutionChain.getHandler());
        assertTrue(view instanceof ViewRenderer);
        WebServerException e =
                assertThrows(WebServerException.class, view::render);
        assertSame(WebServerErrorCode.FRAMEWORK_UNSUPPORTED, e.getErrorCode());
    }

    @Test
    @DisplayName("JsonView를 반환한다.")
    void jsonRendererTest() throws Exception {
        RequestMethod method = RequestMethod.GET;
        String url = "/test1/test2/test3";
        String basePackage = ControllerRequestMappingFinderTestController.class.getPackage().getName();
        HandlerMapping handlerMapping = JSpringFrameworkTestUtils
                .getRequestMappingHandlerMapping(basePackage);
        HandlerAdapter handlerAdapter =
                JSpringFrameworkTestUtils.getRequestMappingHandlerAdapter(basePackage);
        MockHttpServletRequest mockHttpServletRequest =
                JSpringFrameworkTestUtils.newMockHttpServletRequest(method, url);
        HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(mockHttpServletRequest);
        View view = handlerAdapter.handle(mockHttpServletRequest, response, handlerExecutionChain.getHandler());
        assertTrue(view instanceof JsonView);
    }

    @Test
    @DisplayName("custom ExceptionHandler 테스트")
    void exceptionHandlerTest() {
        ExceptionHandlerManager exceptionHandlerManager =
                JSpringFrameworkTestUtils.getExceptionHandlerManager(ExceptionHandler1.class.getPackage().getName());
        assertEquals(ExceptionHandler2.class,
                exceptionHandlerManager.findHandler(new IllegalArgumentException()).getClass());
        assertEquals(ExceptionHandler1.class,
                exceptionHandlerManager.findHandler(new IllegalStateException()).getClass());
        assertEquals(DefaultExceptionHandler.class,
                exceptionHandlerManager.findHandler(new RuntimeException()).getClass());
    }

    @Test
    @DisplayName("custom Interceptor 테스트")
    void interceptorTest() throws Exception {
        RequestMethod method = RequestMethod.GET;
        String url = "/handle";
        String basePackage = InterceptorController.class.getPackage().getName();
        HandlerMapping handlerMapping = JSpringFrameworkTestUtils
                .getRequestMappingHandlerMapping(basePackage);
        HandlerAdapter handlerAdapter =
                JSpringFrameworkTestUtils.getRequestMappingHandlerAdapter(basePackage);
        MockHttpServletRequest mockHttpServletRequest =
                JSpringFrameworkTestUtils.newMockHttpServletRequest(method, url);
        HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(mockHttpServletRequest);
        handlerExecutionChain.applyPreHandle(mockHttpServletRequest, response);
        View view = handlerAdapter.handle(mockHttpServletRequest, response, handlerExecutionChain.getHandler());
        assertTrue(view instanceof JsonView);
        assertSame(Test1Interceptor.class, mockHttpServletRequest.getAttribute(Test1Interceptor.class.getName()));
        assertSame(Test2Interceptor.class, mockHttpServletRequest.getAttribute(Test2Interceptor.class.getName()));
        assertSame(Test3Interceptor.class, mockHttpServletRequest.getAttribute(Test3Interceptor.class.getName()));
    }
}