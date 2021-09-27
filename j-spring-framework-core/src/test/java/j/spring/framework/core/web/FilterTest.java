package j.spring.framework.core.web;

import j.spring.framework.core.JSpringFrameworkTestUtils;
import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.mock.MockServletContext;
import j.spring.framework.core.testdata.web.filter.MyFilter1;
import j.spring.framework.core.testdata.web.filter.MyFilter2;
import j.spring.framework.core.web.mvc.filter.CharacterEncodingFilter;
import j.spring.framework.core.web.mvc.filter.FilterRegistry;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.Filter;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class FilterTest {

    @Test
    @DisplayName("Filter 테스트")
    void filterTest() {
        String basePackage = MyFilter1.class.getPackage().getName();
        ApplicationContext applicationContext = JSpringFrameworkTestUtils.getApplicationContext(basePackage);
        MockServletContext mockServletContext = new MockServletContext();
        FilterRegistry filterInitializer = new FilterRegistry(mockServletContext);
        filterInitializer.register(applicationContext);
        List<Filter> filters = mockServletContext.getFilters();
        assertIterableEquals(Lists.newArrayList(CharacterEncodingFilter.class, MyFilter2.class, MyFilter1.class),
                mockServletContext.getFilters().stream().map(Filter::getClass).collect(Collectors.toList()));
    }

}