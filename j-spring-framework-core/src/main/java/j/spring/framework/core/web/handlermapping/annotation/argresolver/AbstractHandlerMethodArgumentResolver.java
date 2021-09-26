package j.spring.framework.core.web.handlermapping.annotation.argresolver;

import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.converter.ConverterManager;

public abstract class AbstractHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    protected final ConverterManager converterManager;

    public AbstractHandlerMethodArgumentResolver(ApplicationContext applicationContext) {
        converterManager = new ConverterManager(applicationContext);
    }

}
