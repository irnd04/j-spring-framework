package j.shop.app.exception;

import j.shop.app.web.ShopResponse;
import j.spring.framework.core.web.annotation.Component;
import j.spring.framework.core.web.view.JsonView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HttpErrorHandler implements j.spring.framework.core.web.server.error.HttpErrorHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUrl = (String)
                request.getAttribute("javax.servlet.error.request_uri");
        ShopResponse<?> r =
                ShopResponse.failure(new ErrorCodeWithMessage(ShopErrorCode.NOT_FOUND, requestUrl));
        new JsonView(r, response).render();
    }
}
