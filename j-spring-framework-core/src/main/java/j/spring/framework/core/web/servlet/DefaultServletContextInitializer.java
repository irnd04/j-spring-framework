package j.spring.framework.core.web.servlet;

import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.annotation.OrderAnnotationUtils;
import j.spring.framework.core.web.config.StaticResourceConfig;
import j.spring.framework.core.web.mvc.filter.CharacterEncodingFilter;
import j.spring.framework.core.web.mvc.filter.FilterRegistry;
import j.spring.framework.core.web.server.error.HttpErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.util.List;

public class DefaultServletContextInitializer implements ServletContextInitializer {

    private final ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(DefaultServletContextInitializer.class);

    public DefaultServletContextInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        registerServlet(servletContext);
        registerFilter(servletContext);
        logger.info("servlet context initialized.");
    }

    private void registerServlet(ServletContext servletContext) {
        registerStaticResourceServlet(servletContext);
        registerDispatcherServlet(servletContext);
        registerErrorHandleServlet(servletContext);
    }

    private void registerStaticResourceServlet(ServletContext servletContext) {
        StaticResourceConfig config = applicationContext.findOne(StaticResourceConfig.class);
        if (config != null) {
            ServletRegistration.Dynamic registration =
                    servletContext.addServlet(StaticResourceServlet.NAME, new StaticResourceServlet(config.getResourcePrefix()));
            registration.setLoadOnStartup(1);
            registration.addMapping(config.getResourceMappingUrlPatterns().toArray(new String[0]));
            logger.debug("register staticResourceServlet..");
        }
    }

    private void registerDispatcherServlet(ServletContext servletContext) {
        ServletRegistration.Dynamic servletRegistration =
                servletContext.addServlet(DispatcherServlet.NAME, new DispatcherServlet(applicationContext));
        servletRegistration.setLoadOnStartup(2);
        servletRegistration.addMapping("/");
        logger.debug("register dispatcherServlet..");
    }

    private void registerErrorHandleServlet(ServletContext servletContext) {
        HttpErrorHandler httpErrorHandler = applicationContext.findOne(HttpErrorHandler.class);
        if (httpErrorHandler != null) {
            ServletRegistration.Dynamic servletRegistration = servletContext.addServlet(
                    ErrorHandleServlet.NAME, new ErrorHandleServlet(httpErrorHandler));
            servletRegistration.setLoadOnStartup(3);
            servletRegistration.addMapping(ErrorHandleServlet.LOCATION);
            logger.debug("{} http error handler added..", httpErrorHandler.getClass().getName());
        }
    }

    private void registerFilter(ServletContext servletContext) {
        new FilterRegistry(servletContext).register(applicationContext);
    }
}
