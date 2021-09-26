package j.spring.framework.core.web.exceptionhandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExceptionHandler {
    boolean supports(Class<?> clazz);
    void handle(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException, ServletException;
}
