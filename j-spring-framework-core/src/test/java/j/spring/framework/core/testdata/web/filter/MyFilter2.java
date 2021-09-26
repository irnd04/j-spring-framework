package j.spring.framework.core.testdata.web.filter;

import j.spring.framework.core.web.annotation.Component;
import j.spring.framework.core.web.annotation.Order;

import javax.servlet.*;
import java.io.IOException;

@Order(5)
@Component
public class MyFilter2 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
