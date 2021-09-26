package j.spring.framework.core.web.exceptionhandler;

import j.spring.framework.core.web.annotation.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(Integer.MAX_VALUE - 7890)
public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public boolean supports(Class<?> clazz) {
        return Exception.class.isAssignableFrom(clazz);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException, ServletException {
        exception.printStackTrace();
        throw new ServletException(exception);
    }
}
