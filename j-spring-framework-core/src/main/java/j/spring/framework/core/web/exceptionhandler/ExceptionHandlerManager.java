package j.spring.framework.core.web.exceptionhandler;

import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;
import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.annotation.OrderAnnotationUtils;

import java.util.List;

public class ExceptionHandlerManager {
    private final List<ExceptionHandler> exceptionHandlers;

    public ExceptionHandlerManager(ApplicationContext container) {
        List<ExceptionHandler> exceptionHandlers = container.find(ExceptionHandler.class);
        exceptionHandlers.add(new DefaultExceptionHandler());
        this.exceptionHandlers = OrderAnnotationUtils.ordered(exceptionHandlers);
    }

    public ExceptionHandler findHandler(Exception e) {
        for (ExceptionHandler handler : exceptionHandlers) {
            if (handler.supports(e.getClass())) {
                return handler;
            }
        }
        throw WebServerException.of(WebServerErrorCode.NOT_FOUND_EXCEPTION_HANDLER);
    }

}
