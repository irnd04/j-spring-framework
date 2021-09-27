package j.spring.framework.core.web.mvc.filter;

import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.annotation.OrderAnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.List;

public class FilterRegistry {
    private final static Logger logger = LoggerFactory.getLogger(FilterRegistry.class);
    private final ServletContext context;

    public FilterRegistry(ServletContext servletContext) {
        this.context = servletContext;
    }

    public void register(ApplicationContext applicationContext) {
        List<Filter> filters = applicationContext.find(Filter.class);
        filters.add(new CharacterEncodingFilter());
        filters = OrderAnnotationUtils.ordered(filters);
        for (Filter filter : filters) {
            FilterRegistration.Dynamic filterRegistration = context.addFilter(filter.getClass().getName(), filter);
            filterRegistration.addMappingForUrlPatterns(null, false, "/*");
            logger.debug(filter.getClass().getName() + " filter added..");
        }
    }

}
