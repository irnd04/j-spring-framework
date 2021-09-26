package j.spring.framework.core.testdata.web.exceptionhandler;

import j.spring.framework.core.web.annotation.Component;
import j.spring.framework.core.web.annotation.Order;
import j.spring.framework.core.web.exceptionhandler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(-100)
@Component
public class ExceptionHandler2 implements ExceptionHandler {

    @Override
    public boolean supports(Class<?> clazz) {
        return IllegalArgumentException.class.isAssignableFrom(clazz);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {

    }
}
