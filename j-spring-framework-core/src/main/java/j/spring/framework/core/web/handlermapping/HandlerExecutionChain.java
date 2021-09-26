package j.spring.framework.core.web.handlermapping;

import j.spring.framework.core.web.interceptor.HandlerInterceptorManager;
import j.spring.framework.core.web.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionChain {

    private final Object handler;
    private final HandlerInterceptorManager handlerInterceptorManager;

    public HandlerExecutionChain(Object handler, HandlerInterceptorManager handlerInterceptorManager) {
        this.handler = handler;
        this.handlerInterceptorManager = handlerInterceptorManager;
    }

    public Object getHandler() {
        return handler;
    }

    public void applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        handlerInterceptorManager.applyPreHandle(request, response, handler);
    }

    public void applyPostHandle(HttpServletRequest request, HttpServletResponse response, View view) throws Exception {
        handlerInterceptorManager.applyPostHandle(request, response, handler, view);
    }

}
