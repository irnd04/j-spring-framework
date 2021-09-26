package j.spring.framework.core.testdata.web.exceptionhandler;

import j.spring.framework.core.web.annotation.Component;
import j.spring.framework.core.web.annotation.Order;
import j.spring.framework.core.web.exceptionhandler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Order(-99)
@Component
public class ExceptionHandler1 implements ExceptionHandler {
    @Override
    public boolean supports(Class<?> clazz) {
        return IllegalStateException.class.isAssignableFrom(clazz);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Exception exception) {

    }
}
