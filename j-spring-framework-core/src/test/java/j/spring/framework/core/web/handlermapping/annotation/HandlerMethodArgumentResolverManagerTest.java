package j.spring.framework.core.web.handlermapping.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;
import j.spring.framework.core.mock.MockHttpServletRequest;
import j.spring.framework.core.testdata.web.handlermapping.annotation.resolver.*;
import j.spring.framework.core.testdata.web.handlermapping.annotation.resolver.pathvariable.PathVariableController;
import j.spring.framework.core.testdata.web.handlermapping.annotation.resolver.requestbody.RequestBodyController;
import j.spring.framework.core.testdata.web.handlermapping.annotation.resolver.requestparam.RequestParameterController;
import j.spring.framework.core.web.annotation.RequestMethod;
import j.spring.framework.core.web.handlermapping.HandlerMapping;
import j.spring.framework.core.web.handlermapping.annotation.argresolver.HandlerMethodArgumentResolverManager;
import j.spring.framework.core.web.util.RequestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static j.spring.framework.core.JSpringFrameworkTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class HandlerMethodArgumentResolverManagerTest {

    private final RequestBodyValue requestBodyValue = new RequestBodyValue();
    private final RequestBodyValue2 requestBodyValue2 = new RequestBodyValue2();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockHttpServletRequest newMockHttpServletRequest(RequestMethod method, String url) {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setMethod(method.name());
        mockHttpServletRequest.setRequestURI(url);
        Map<String, String[]> map = new HashMap<>();
        map.put("var1", new String[] {"var1"});
        map.put("var2", new String[] {"var1", "var2"});
        map.put("int1", new String[] {"1"});
        map.put("int2", new String[] {"1", "2"});
        mockHttpServletRequest.setParameterMap(map);
        return mockHttpServletRequest;
    }

    @Test
    @DisplayName("PathVariable converter에서 convert가 불가능할 경우 오류가 발생합니다.")
    void convertIsNotPossibleTest() throws Exception {
        String url = "/test/good";
        String basePackage = PathVariableController.class.getPackage().getName();
        HandlerMapping handlerMapping = getRequestMappingHandlerMapping(basePackage);
        MockHttpServletRequest mockHttpServletRequest = newMockHttpServletRequest(RequestMethod.POST, url);
        RequestMappingMethod requestMappingMethod = (RequestMappingMethod) handlerMapping.getHandler(mockHttpServletRequest)
                .getHandler();
        HandlerMethodArgumentResolverManager handlerMethodArgumentResolverManager =
                getHandlerMethodArgumentResolverManager(basePackage);
        NumberFormatException e = assertThrows(NumberFormatException.class,
                () -> handlerMethodArgumentResolverManager.getParameterValues(mockHttpServletRequest, requestMappingMethod));
    }

    @Test
    @DisplayName("PathVariable 주입 테스트")
    void pathVarInjectionTest() throws Exception {
        String url = "/test";
        int pathVar = 31;
        url += "/" + pathVar;
        String basePackage = PathVariableController.class.getPackage().getName();
        HandlerMapping handlerMapping = getRequestMappingHandlerMapping(basePackage);
        MockHttpServletRequest mockHttpServletRequest = newMockHttpServletRequest(RequestMethod.POST, url);
        RequestMappingMethod requestMappingMethod = (RequestMappingMethod) handlerMapping.getHandler(mockHttpServletRequest)
                .getHandler();
        HandlerMethodArgumentResolverManager handlerMethodArgumentResolverManager =
                getHandlerMethodArgumentResolverManager(basePackage);
        Object[] parameterValues =
                handlerMethodArgumentResolverManager.getParameterValues(mockHttpServletRequest, requestMappingMethod);
        assertSame(pathVar, parameterValues[0]);
    }

    @Test
    @DisplayName("PathVariable을 파싱할때 접미사가 / 여도 오류가 발생하지 않는다.")
    void urlSuffixSlashTest() throws Exception {
        String url = "////////////////test";
        int pathVar = 31;
        url += "///////////" + pathVar + "////////////////////////";
        String basePackage = PathVariableController.class.getPackage().getName();
        HandlerMapping handlerMapping = getRequestMappingHandlerMapping(basePackage);
        MockHttpServletRequest mockHttpServletRequest = newMockHttpServletRequest(RequestMethod.POST, url);
        RequestMappingMethod requestMappingMethod = (RequestMappingMethod) handlerMapping.getHandler(mockHttpServletRequest)
                .getHandler();
        HandlerMethodArgumentResolverManager handlerMethodArgumentResolverManager =
                getHandlerMethodArgumentResolverManager(basePackage);
        Object[] parameterValues =
                handlerMethodArgumentResolverManager.getParameterValues(mockHttpServletRequest, requestMappingMethod);
        assertSame(pathVar, parameterValues[0]);
    }

    @Test
    @DisplayName("PathVariable 이름이 동일하지 않으면 찾지 못한다.")
    void pathVarNameNotFoundTest() throws Exception {
        String url = "/test/1/2";
        String basePackage = PathVariableController.class.getPackage().getName();
        HandlerMapping handlerMapping = getRequestMappingHandlerMapping(basePackage);
        MockHttpServletRequest mockHttpServletRequest = newMockHttpServletRequest(RequestMethod.PUT, url);
        RequestMappingMethod requestMappingMethod = (RequestMappingMethod) handlerMapping.getHandler(mockHttpServletRequest)
                .getHandler();
        HandlerMethodArgumentResolverManager handlerMethodArgumentResolverManager =
                getHandlerMethodArgumentResolverManager(basePackage);
        WebServerException e
                = assertThrows(WebServerException.class, () -> handlerMethodArgumentResolverManager.getParameterValues(mockHttpServletRequest, requestMappingMethod));
        assertSame(WebServerErrorCode.PATH_VARIABLE_NOT_FOUND, e.getErrorCode());
    }

    @Test
    @DisplayName("request parameter 테스트")
    void requestParameterTest() throws Exception {
        String url = "/param/var1int1";
        String basePackage = RequestParameterController.class.getPackage().getName();
        HandlerMapping handlerMapping = getRequestMappingHandlerMapping(basePackage);
        MockHttpServletRequest mockHttpServletRequest = newMockHttpServletRequest(RequestMethod.GET, url);
        RequestMappingMethod requestMappingMethod = (RequestMappingMethod) handlerMapping.getHandler(mockHttpServletRequest)
                .getHandler();
        HandlerMethodArgumentResolverManager handlerMethodArgumentResolverManager =
                getHandlerMethodArgumentResolverManager(basePackage);
        Object[] parameterValues =
                handlerMethodArgumentResolverManager.getParameterValues(mockHttpServletRequest, requestMappingMethod);
        assertArrayEquals(new Object[] {"var1", 1}, parameterValues);
    }

    @Test
    @DisplayName("request parameter가 배열일 경우 requestMappingMethod의 parameter가 배열이 아니라면 0번 인덱스의 값이 주입된다.")
    void requestParameterTypeIsArrayTest() throws Exception {
        String url = "/param/var1var2";
        String basePackage = RequestParameterController.class.getPackage().getName();
        HandlerMapping handlerMapping = getRequestMappingHandlerMapping(basePackage);
        MockHttpServletRequest mockHttpServletRequest = newMockHttpServletRequest(RequestMethod.PUT, url);
        RequestMappingMethod requestMappingMethod = (RequestMappingMethod) handlerMapping.getHandler(mockHttpServletRequest)
                .getHandler();
        HandlerMethodArgumentResolverManager handlerMethodArgumentResolverManager =
                getHandlerMethodArgumentResolverManager(basePackage);
        Object[] parameterValues =
                handlerMethodArgumentResolverManager.getParameterValues(mockHttpServletRequest, requestMappingMethod);
        assertArrayEquals(new Object[] {"var1", "var1"}, parameterValues);
    }

    @Test
    @DisplayName("request parameter가 배열이 아니고 requestMappingMethod의 parameter가 배열이라면 1개의 길이를 가진 배열이 주입된다.")
    void requestParameterTypeIsStringTest() throws Exception {
        String url = "/param/int1int2";
        String basePackage = RequestParameterController.class.getPackage().getName();
        HandlerMapping handlerMapping = getRequestMappingHandlerMapping(basePackage);
        MockHttpServletRequest mockHttpServletRequest = newMockHttpServletRequest(RequestMethod.DELETE, url);
        RequestMappingMethod requestMappingMethod = (RequestMappingMethod) handlerMapping.getHandler(mockHttpServletRequest)
                .getHandler();
        HandlerMethodArgumentResolverManager handlerMethodArgumentResolverManager =
                getHandlerMethodArgumentResolverManager(basePackage);
        Object[] parameterValues =
                handlerMethodArgumentResolverManager.getParameterValues(mockHttpServletRequest, requestMappingMethod);
        assertArrayEquals(new Object[] {new int[] {1}, new int[] {1, 2}}, parameterValues);
    }

    @Test
    @DisplayName("request body 주입 테스트")
    void requestBodyInjectionTest() throws Exception {
        String url = "/rb/one";
        String basePackage = RequestBodyController.class.getPackage().getName();
        HandlerMapping handlerMapping = getRequestMappingHandlerMapping(basePackage);
        MockHttpServletRequest mockHttpServletRequest = newMockHttpServletRequest(RequestMethod.DELETE, url);
        RequestMappingMethod requestMappingMethod = (RequestMappingMethod) handlerMapping.getHandler(mockHttpServletRequest)
                .getHandler();
        HandlerMethodArgumentResolverManager handlerMethodArgumentResolverManager =
                getHandlerMethodArgumentResolverManager(basePackage);
        requestBodyValue.setValue("value");
        requestBodyValue.setI(1);
        String body = objectMapper.writeValueAsString(requestBodyValue);
        mockHttpServletRequest.setBody(body);
        Object[] parameterValues =
                handlerMethodArgumentResolverManager.getParameterValues(mockHttpServletRequest, requestMappingMethod);
        org.assertj.core.api.Assertions.assertThat(parameterValues)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(new Object[] {requestBodyValue});
    }

    @Test
    @DisplayName("request body를 한 메서드에 두개 사용하면 오류발생")
    void multipleRequestBodyInjectionTest() throws Exception {
        String url = "/rb/two";
        String basePackage = RequestBodyController.class.getPackage().getName();
        HandlerMapping handlerMapping = getRequestMappingHandlerMapping(basePackage);
        MockHttpServletRequest mockHttpServletRequest = newMockHttpServletRequest(RequestMethod.POST, url);
        RequestMappingMethod requestMappingMethod = (RequestMappingMethod) handlerMapping.getHandler(mockHttpServletRequest)
                .getHandler();
        HandlerMethodArgumentResolverManager handlerMethodArgumentResolverManager =
                getHandlerMethodArgumentResolverManager(basePackage);
        requestBodyValue.setValue("value");
        requestBodyValue.setI(55);
        String body = objectMapper.writeValueAsString(requestBodyValue);
        mockHttpServletRequest.setBody(body);
        WebServerException e = assertThrows(WebServerException.class, () ->
                handlerMethodArgumentResolverManager.getParameterValues(mockHttpServletRequest, requestMappingMethod));
        assertSame(WebServerErrorCode.HTTP_BODY_NOT_READABLE, e.getErrorCode());
    }

    @Test
    @DisplayName("stream이 닫힌 경우 오류발생")
    void closedRequestBodyTest() throws Exception {
        String url = "/rb/stream-closed";
        String basePackage = RequestBodyController.class.getPackage().getName();
        HandlerMapping handlerMapping = getRequestMappingHandlerMapping(basePackage);
        MockHttpServletRequest mockHttpServletRequest = newMockHttpServletRequest(RequestMethod.POST, url);
        RequestMappingMethod requestMappingMethod = (RequestMappingMethod) handlerMapping.getHandler(mockHttpServletRequest)
                .getHandler();
        HandlerMethodArgumentResolverManager handlerMethodArgumentResolverManager =
                getHandlerMethodArgumentResolverManager(basePackage);
        requestBodyValue2.setA("a");
        requestBodyValue2.setD(false);
        String body = objectMapper.writeValueAsString(requestBodyValue2);
        mockHttpServletRequest.setBody(body);
        RequestUtils.readBody(mockHttpServletRequest);
        WebServerException e
                = assertThrows(WebServerException.class, () ->
                handlerMethodArgumentResolverManager.getParameterValues(mockHttpServletRequest, requestMappingMethod));
        assertSame(WebServerErrorCode.HTTP_BODY_NOT_READABLE, e.getErrorCode());
    }

}