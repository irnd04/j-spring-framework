package j.spring.framework.core.web.converter;

import j.spring.framework.core.JSpringFrameworkTestUtils;
import j.spring.framework.core.exception.IocContainerErrorCode;
import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;
import j.spring.framework.core.testdata.web.converter.CustomErrorCodeConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ConverterManagerTest {

    private ConverterManager converterManager;

    @BeforeEach
    void beforeEach() {
        converterManager =
                JSpringFrameworkTestUtils.getConverterManager(CustomErrorCodeConverter.class.getPackage().getName());
    }

    private static Stream<Arguments> provideConvertibleValueTest() {
        return Stream.of(
            Arguments.of("2.13", Double.class, 2.13),
            Arguments.of("123", String.class, "123"),
            Arguments.of(null, int.class, 0),
            Arguments.of(null, boolean.class, false),
            Arguments.of(null, Boolean.class, null),
            Arguments.of("true", Boolean.class, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideConvertibleValueTest")
    public void convertibleValueTest(String s, Class<?> clazz, Object expected) {
        assertEquals(expected, converterManager.convert(s, clazz));
    }

    @Test
    @DisplayName("converter를 찾을 수 없다면 오류 발생")
    void notFoundConverter() {
        WebServerException e =
                assertThrows(WebServerException.class, () -> converterManager.convert("gg", IocContainerErrorCode.class));
        assertSame(WebServerErrorCode.CONVERTER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    @DisplayName("convert 할 수 없다면 오류 발생")
    void notPossibleConvert() {
        NumberFormatException e =
                assertThrows(NumberFormatException.class, () -> converterManager.convert("gg", Double.class));
    }

    @ParameterizedTest
    @EnumSource(WebServerErrorCode.class)
    @DisplayName("커스텀 컨버터 테스트")
    void customConverterTest(WebServerErrorCode actual) {
        WebServerErrorCode expected
                = (WebServerErrorCode) converterManager.convert(actual.name(), actual.getClass());
        assertSame(expected, actual);
    }

}