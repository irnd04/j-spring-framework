package j.spring.framework.core.web.converter;

public class BooleanConverter implements Converter<Boolean> {
    @Override
    public Boolean convert(String s) {
        if (s == null) {
            return null;
        }
        return Boolean.valueOf(s);
    }
}
