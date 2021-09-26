package j.spring.framework.core.web.servlet;

import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.config.StaticResourceConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class StaticResourceServletInitializer {

    private final ServletContext context;
    private final StaticResourceConfig config;
    public static final String STATIC_RESOURCE_SERVLET_NAME = "staticResourceServlet";

    public StaticResourceServletInitializer(ApplicationContext applicationContext, ServletContext context) {
        this.context = context;
        this.config = applicationContext.findOne(StaticResourceConfig.class);
    }

    public void initialize() {
        if (config != null) {
            ServletRegistration.Dynamic registration =
                    context.addServlet(STATIC_RESOURCE_SERVLET_NAME, new StaticResourceServlet(config.getResourcePrefix()));
            registration.setLoadOnStartup(1);
            registration.addMapping(config.getResourceMappingUrlPatterns().toArray(new String[0]));
        }
    }

}
