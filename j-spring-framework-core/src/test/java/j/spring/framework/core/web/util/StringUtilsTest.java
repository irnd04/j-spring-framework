package j.spring.framework.core.web.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {

    @Test
    @DisplayName("urlJoin 테스트")
    void urlJonTest() {
        assertEquals("/good", StringUtils.urlJoin("/good", ""));
        assertEquals("/good/2", StringUtils.urlJoin("/good", "///2"));
        assertEquals("/good", StringUtils.urlJoin("/good", "/"));
        assertEquals("/good/good", StringUtils.urlJoin("/good", "", "", "good"));
        assertEquals("/good", StringUtils.urlJoin("", "good"));
        assertEquals("/good", StringUtils.urlJoin("/good", "//"));
        assertEquals("/good", StringUtils.urlJoin("good", "/"));
        assertEquals("/good", StringUtils.urlJoin("good"));
        assertEquals("/good/{1}/lo/{2}", StringUtils.urlJoin("good", "{1}/lo", "{2}", "/"));
    }

}