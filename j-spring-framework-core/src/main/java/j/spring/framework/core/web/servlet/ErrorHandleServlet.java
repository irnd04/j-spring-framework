package j.spring.framework.core.web.servlet;

import j.spring.framework.core.web.server.error.HttpErrorHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandleServlet extends HttpServlet {

    public static final String NAME = "errorHandleServlet";
    public static final String LOCATION = "/error";
    private final HttpErrorHandler httpErrorHandler;

    public ErrorHandleServlet(HttpErrorHandler httpErrorHandler) {
        this.httpErrorHandler = httpErrorHandler;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        httpErrorHandler.handle(req, resp);
    }

}
