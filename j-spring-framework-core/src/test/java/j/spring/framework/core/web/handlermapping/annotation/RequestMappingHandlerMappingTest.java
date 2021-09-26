package j.spring.framework.core.web.handlermapping.annotation;

import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;
import j.spring.framework.core.testdata.web.handlermapping.annotation.methodhandler.controller.ControllerRequestMappingFinderTestController;
import j.spring.framework.core.testdata.web.handlermapping.annotation.methodhandler.duplicate.RequestMappingDuplicateTestController;
import j.spring.framework.core.testdata.web.handlermapping.annotation.methodhandler.method.MethodRequestMappingFinderTestController;
import j.spring.framework.core.web.annotation.RequestMethod;
import j.spring.framework.core.web.handlermapping.HandlerMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static j.spring.framework.core.JSpringFrameworkTestUtils.*;
import static j.spring.framework.core.JSpringFrameworkTestUtils.getRequestMappingMethod;
import static org.junit.jupiter.api.Assertions.*;

class RequestMappingHandlerMappingTest {

    @Test
    @DisplayName("requestMapping의 method기본값은 RequestMethod 전부 이다.")
    void requestMappingTest() throws Exception {
        String url = "/test";
        String basePackage = MethodRequestMappingFinderTestController.class.getPackage().getName();
        HandlerMapping handlerMapping =
                getRequestMappingHandlerMapping(basePackage);
        RequestMappingMethod get = getRequestMappingMethod(handlerMapping, RequestMethod.GET, url);
        assertNotNull(get);
        assertSame(get, getRequestMappingMethod(handlerMapping, RequestMethod.POST, url));
        assertSame(get, getRequestMappingMethod(handlerMapping, RequestMethod.PUT, url));
        assertSame(get, getRequestMappingMethod(handlerMapping, RequestMethod.DELETE, url));
        assertFalse(get.hasResponseBody());
    }

    @Test
    @DisplayName("requestMapping의 method를 지정할 수 있다.")
    void getOrPost() throws Exception {
        String url = "/getOrPost";
        String basePackage = MethodRequestMappingFinderTestController.class.getPackage().getName();
        HandlerMapping handlerMapping =
                getRequestMappingHandlerMapping(basePackage);
        RequestMappingMethod get = getRequestMappingMethod(handlerMapping, RequestMethod.GET, url);
        assertNotNull(get);
        assertSame(get, getRequestMappingMethod(handlerMapping, RequestMethod.POST, url));
        assertNull(getRequestMappingMethod(handlerMapping, RequestMethod.PUT, url));
        assertNull(getRequestMappingMethod(handlerMapping, RequestMethod.DELETE, url));
        assertFalse(get.hasResponseBody());
    }

    @Test
    @DisplayName("두개이상의 url이 매치되면 구체적인 url이 선택된다.")
    void multipleMatch() throws Exception {
        String url = "/test/test/1";
        String basePackage = MethodRequestMappingFinderTestController.class.getPackage().getName();
        HandlerMapping handlerMapping =
                getRequestMappingHandlerMapping(basePackage);
        RequestMappingMethod requestMappingMethod = getRequestMappingMethod(handlerMapping, RequestMethod.GET, url);
        assertEquals(MethodRequestMappingFinderTestController.class, requestMappingMethod.getInstance().getClass());
        assertEquals(url, requestMappingMethod.getMappingUrl());
        assertFalse(requestMappingMethod.hasResponseBody());
    }

    @Test
    @DisplayName("PathVariable을 사용할 수 있다.")
    void pathVariableUse() throws Exception {
        String url = "/test/test/g";
        String basePackage = MethodRequestMappingFinderTestController.class.getPackage().getName();
        HandlerMapping handlerMapping =
                getRequestMappingHandlerMapping(basePackage);
        RequestMappingMethod requestMappingMethod = getRequestMappingMethod(handlerMapping, RequestMethod.GET, url);
        assertEquals(MethodRequestMappingFinderTestController.class, requestMappingMethod.getInstance().getClass());
        assertEquals("/test/test/{number}", requestMappingMethod.getMappingUrl());
        assertFalse(requestMappingMethod.hasResponseBody());
    }

    @Test
    @DisplayName("모호한 url mapping 오류 발생")
    void AmbiguousRequestMapping() {
        String url = "/gg/gg";
        String basePackage = MethodRequestMappingFinderTestController.class.getPackage().getName();
        HandlerMapping handlerMapping =
                getRequestMappingHandlerMapping(basePackage);
        WebServerException e
                = assertThrows(WebServerException.class, () ->
                        getRequestMappingMethod(handlerMapping, RequestMethod.POST, url));
        assertSame(WebServerErrorCode.AMBIGUOUS_REQUEST_MAPPING, e.getErrorCode());
    }

    @Test
    @DisplayName("메서드에 responseBody 테스트")
    void responseBodyTest() throws Exception {
        String url = "/responsebody";
        String basePackage = MethodRequestMappingFinderTestController.class.getPackage().getName();
        HandlerMapping handlerMapping =
                getRequestMappingHandlerMapping(basePackage);
        RequestMappingMethod requestMappingMethod = getRequestMappingMethod(handlerMapping, RequestMethod.GET, url);
        assertEquals(MethodRequestMappingFinderTestController.class, requestMappingMethod.getInstance().getClass());
        assertTrue(requestMappingMethod.hasResponseBody());
    }

    @Test
    @DisplayName("콘트롤러 RequestMapping & ResponseBody 테스트")
    void controllerRequestMappingAndResponseBodyTest() throws Exception {
        String url = "/test1/test2/test3";
        String basePackage = ControllerRequestMappingFinderTestController.class.getPackage().getName();
        HandlerMapping handlerMapping =
                getRequestMappingHandlerMapping(basePackage);
        RequestMappingMethod requestMappingMethod = getRequestMappingMethod(handlerMapping, RequestMethod.GET, url);
        assertEquals(ControllerRequestMappingFinderTestController.class, requestMappingMethod.getInstance().getClass());
        assertTrue(requestMappingMethod.hasResponseBody());
    }

    @Test
    @DisplayName("찾을 수 없는 경우 null을 반환한다.")
    void notFoundTest() throws Exception {
        String url = "/zffd/weq/1";
        String basePackage = ControllerRequestMappingFinderTestController.class.getPackage().getName();
        HandlerMapping handlerMapping =
                getRequestMappingHandlerMapping(basePackage);
        RequestMappingMethod requestMappingMethod = getRequestMappingMethod(handlerMapping, RequestMethod.GET, url);
        assertNull(requestMappingMethod);
    }

    @Test
    @DisplayName("중복 requestMapping 발견시 오류 발생")
    void duplicateCheck() {
        WebServerException e
                = assertThrows(WebServerException.class, () ->
                getRequestMappingHandlerMapping(RequestMappingDuplicateTestController.class.getPackage().getName()));
        assertSame(WebServerErrorCode.DUPLICATE_REQUEST_MAPPING, e.getErrorCode());
    }

    @Test
    @DisplayName("접미사가 /인 경우도 찾을 수 있다.")
    void urlSuffixSlashTest() throws Exception {
        String url = "///////////////test1////////test2///////////test3///////////";
        String basePackage = ControllerRequestMappingFinderTestController.class.getPackage().getName();
        HandlerMapping handlerMapping =
                getRequestMappingHandlerMapping(basePackage);
        RequestMappingMethod requestMappingMethod = getRequestMappingMethod(handlerMapping, RequestMethod.GET, url);
        assertEquals(ControllerRequestMappingFinderTestController.class, requestMappingMethod.getInstance().getClass());
        assertTrue(requestMappingMethod.hasResponseBody());
    }


}