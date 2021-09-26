package j.shop.app.interceptor;


import j.spring.framework.core.web.annotation.Component;
import j.spring.framework.core.web.interceptor.HandlerInterceptor;
import j.spring.framework.core.web.util.RequestUtils;
import j.spring.framework.core.web.view.JsonView;
import j.spring.framework.core.web.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public void preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuilder buffer = new StringBuilder();

        buffer.append(request.getMethod()).append(' ');

        buffer.append(request.getRequestURL());

        String qs = request.getQueryString();
        if (qs != null) {
            buffer.append('?').append(qs);
        }

        buffer.append(' ');

        String body = RequestUtils.readBody(request);
        buffer.append(body);
        logger.debug("request : " + buffer);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, View view) throws Exception {
        if (view instanceof JsonView) {
            JsonView jsonView = (JsonView) view;
            logger.debug("response : " + jsonView.getJson());
        }
    }

}
