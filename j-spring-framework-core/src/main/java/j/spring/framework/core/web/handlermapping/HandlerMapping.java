package j.spring.framework.core.web.handlermapping;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {

    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;

}
