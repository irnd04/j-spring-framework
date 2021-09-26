package j.spring.framework.core.web.servlet;

import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.mvc.filter.FilterInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class DefaultServletContextInitializer implements ServletContextInitializer {

    public static final String DISPATCHER_SERVLET_NAME = "dispatcherServlet";
    private final ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(DefaultServletContextInitializer.class);

    public DefaultServletContextInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(applicationContext));
        servletRegistration.setLoadOnStartup(2);
        servletRegistration.addMapping("/");

        new FilterInitializer(applicationContext, servletContext).initialize();
        new StaticResourceServletInitializer(applicationContext, servletContext).initialize();

        logger.info("servlet context initialized.");
    }
}
