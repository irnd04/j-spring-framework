package j.spring.framework.core.testdata.web.interceptor;

import j.spring.framework.core.web.annotation.Component;
import j.spring.framework.core.web.annotation.Order;
import j.spring.framework.core.web.interceptor.HandlerInterceptor;
import j.spring.framework.core.web.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Order(41)
@Component
public class Test3Interceptor implements HandlerInterceptor {

    @Override
    public void preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(this.getClass().getName(), this.getClass());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, View view) throws Exception {

    }

}
