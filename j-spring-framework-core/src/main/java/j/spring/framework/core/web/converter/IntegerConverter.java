package j.spring.framework.core.web.converter;

import j.spring.framework.core.web.util.StringUtils;

public class IntegerConverter implements Converter<Integer> {

    @Override
    public Integer convert(String s) {
        if (StringUtils.isBlank(s))  {
            return null;
        }
        return Integer.parseInt(s);
    }
}
