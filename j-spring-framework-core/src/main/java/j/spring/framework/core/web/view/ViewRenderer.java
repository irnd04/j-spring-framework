package j.spring.framework.core.web.view;

import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;

import java.io.IOException;

public class ViewRenderer implements View {

    @Override
    public void render() throws IOException {
        throw WebServerException
                .of(WebServerErrorCode.FRAMEWORK_UNSUPPORTED, "view renderer");
    }
}
