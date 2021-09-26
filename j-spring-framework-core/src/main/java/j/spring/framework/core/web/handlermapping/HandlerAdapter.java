package j.spring.framework.core.web.handlermapping;

import j.spring.framework.core.web.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    boolean supports(Object handler);
    View handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

}
