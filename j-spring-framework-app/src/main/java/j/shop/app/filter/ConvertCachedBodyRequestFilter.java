package j.shop.app.filter;

import j.shop.app.web.CachedBodyHttpServletRequest;
import j.spring.framework.core.web.annotation.Component;
import j.spring.framework.core.web.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(1)
@Component
public class ConvertCachedBodyRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new CachedBodyHttpServletRequest((HttpServletRequest) request), response);
    }

    @Override
    public void destroy() {

    }
}
