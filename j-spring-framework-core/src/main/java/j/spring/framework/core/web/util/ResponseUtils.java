package j.spring.framework.core.web.util;

import j.spring.framework.core.web.view.ContentType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ResponseUtils {

    private ResponseUtils() {

    }

    public static void write(HttpServletResponse response, ContentType contentType, String string) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(contentType.getContentType());
        try (PrintWriter writer = response.getWriter()) {
            writer.print(string);
            writer.flush();
        }
    }

    public static void write(HttpServletResponse response, ContentType contentType, InputStream inputStream) throws IOException {
        String s = StringUtils.fromInputStream(inputStream);
        write(response, contentType, s);
    }

}
