package j.spring.framework.core.testdata.web.converter;

import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.web.annotation.Component;
import j.spring.framework.core.web.converter.Converter;

@Component
public class CustomErrorCodeConverter implements Converter<WebServerErrorCode> {
    @Override
    public WebServerErrorCode convert(String s) {
        return WebServerErrorCode.valueOf(s);
    }
}
