package j.spring.framework.core.web.util;

import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestUtils {

    private RequestUtils() {}

    public static String readBody(HttpServletRequest request) {
        try {
            return StringUtils.fromInputStream(request.getInputStream());
        } catch (IOException e) {
            throw WebServerException.of(WebServerErrorCode.HTTP_BODY_NOT_READABLE, e);
        }
    }

}
