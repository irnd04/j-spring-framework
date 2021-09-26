package j.shop.app.exception;

import j.shop.app.web.ShopResponse;
import j.spring.framework.core.web.annotation.Component;
import j.spring.framework.core.web.annotation.Order;
import j.spring.framework.core.web.exceptionhandler.ExceptionHandler;
import j.spring.framework.core.web.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(-1)
@Component
public class ServerErrorExceptionHandler implements ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerErrorExceptionHandler.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return Exception.class.isAssignableFrom(clazz);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException, ServletException {
        logger.info(exception.getMessage(), exception);
        ShopResponse<?> r = ShopResponse.failure(ShopErrorCode.SERVER_ERROR);
        new JsonView(r, response).render();
    }
}
