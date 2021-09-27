package j.spring.framework.core.web.servlet;

import org.apache.catalina.servlets.DefaultServlet;

import javax.servlet.http.HttpServletRequest;

public class StaticResourceServlet extends DefaultServlet {

    private final String prefix;
    public static final String NAME = "staticResourceServlet";;

    public StaticResourceServlet(String prefix) {
        this.prefix = prefix;
    }

    @Override
    protected String getRelativePath(HttpServletRequest request, boolean allowEmptyPath) {
        return prefix + super.getRelativePath(request, allowEmptyPath);
    }
}
