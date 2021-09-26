package j.spring.framework.core.web.converter;

import j.spring.framework.core.web.util.StringUtils;

public class LongConverter implements Converter<Long> {
    @Override
    public Long convert(String s) {
        if (StringUtils.isBlank(s))  {
            return null;
        }
        return Long.parseLong(s);
    }
}
