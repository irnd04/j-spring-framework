package j.spring.framework.core.web.interceptor;

import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.annotation.OrderAnnotationUtils;
import j.spring.framework.core.web.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HandlerInterceptorManager {

    private final List<HandlerInterceptor> interceptors;
    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptorManager.class);

    public HandlerInterceptorManager(ApplicationContext applicationContext) {
        List<HandlerInterceptor> interceptors = applicationContext.find(HandlerInterceptor.class);
        this.interceptors = OrderAnnotationUtils.ordered(interceptors);
        this.interceptors.forEach(interceptor -> logger.debug("{} interceptor added..", interceptor.getClass().getName()));
    }

    public void applyPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        for (HandlerInterceptor handlerInterceptor : interceptors) {
            handlerInterceptor.preHandle(request, response, handler);
        }
    }

    public void applyPostHandle(HttpServletRequest request, HttpServletResponse response, Object handler, View view) throws Exception {
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            HandlerInterceptor handlerInterceptor = interceptors.get(i);
            handlerInterceptor.postHandle(request, response, handler, view);
        }
    }
}
