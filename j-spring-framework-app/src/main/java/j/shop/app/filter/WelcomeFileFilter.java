package j.shop.app.filter;

import j.spring.framework.core.PathMatcher;
import j.spring.framework.core.web.annotation.Component;
import j.spring.framework.core.web.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(-1)
@Component
public class WelcomeFileFilter implements Filter {

    private static final String WELCOME_FILE = "/index.html";
    private static final PathMatcher PATH_MATCHER = new PathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getMethod().equals("GET") &&
                PATH_MATCHER.match("/", httpServletRequest.getRequestURI())) {
            request.getRequestDispatcher(WELCOME_FILE).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
