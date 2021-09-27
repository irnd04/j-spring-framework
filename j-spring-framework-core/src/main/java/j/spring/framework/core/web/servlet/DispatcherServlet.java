package j.spring.framework.core.web.servlet;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;
import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.exceptionhandler.ExceptionHandlerManager;
import j.spring.framework.core.web.handlermapping.*;
import j.spring.framework.core.web.handlermapping.annotation.RequestMappingHandlerAdapter;
import j.spring.framework.core.web.handlermapping.annotation.RequestMappingHandlerMapping;
import j.spring.framework.core.web.view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * todo : Implementation {@link javax.servlet.Servlet#service}
 */
public class DispatcherServlet extends HttpServlet {

    private final List<HandlerMapping> handlerMappings;
    private final List<HandlerAdapter> handlerAdapters;
    private final ExceptionHandlerManager exceptionHandlerManager;

    public static final String NAME = "dispatcherServlet";

    public DispatcherServlet(ApplicationContext applicationContext) {
        this.handlerMappings = createHandlerMappings(applicationContext);
        this.handlerAdapters = createHandlerAdapters(applicationContext);
        this.exceptionHandlerManager = new ExceptionHandlerManager(applicationContext);
    }

    private List<HandlerMapping> createHandlerMappings(ApplicationContext applicationContext) {
        List<HandlerMapping> handlerMappings = Lists.newArrayList();
        handlerMappings.add(new RequestMappingHandlerMapping(applicationContext));
        return ImmutableList.copyOf(handlerMappings);
    }

    private List<HandlerAdapter> createHandlerAdapters(ApplicationContext applicationContext) {
        List<HandlerAdapter> handlerAdapters = Lists.newArrayList();
        handlerAdapters.add(new RequestMappingHandlerAdapter(applicationContext));
        return ImmutableList.copyOf(handlerAdapters);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            HandlerExecutionChain handlerExecutionChain = getHandler(req);
            if (handlerExecutionChain == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
                return;
            }
            HandlerAdapter handlerAdapter = getHandlerAdapter(handlerExecutionChain.getHandler());
            handlerExecutionChain.applyPreHandle(req, resp);
            View view = handlerAdapter.handle(req, resp, handlerExecutionChain.getHandler());
            handlerExecutionChain.applyPostHandle(req, resp, view);
            view.render();
        } catch (Exception e) {
            exceptionHandlerManager.findHandler(e)
                    .handle(req, resp, e);
        }
    }

    private HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        for (HandlerMapping mapping : this.handlerMappings) {
            HandlerExecutionChain handler = mapping.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
        for (HandlerAdapter adapter : this.handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw WebServerException.of(WebServerErrorCode.NOT_FOUND_HANDLER_ADAPTER);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
}
