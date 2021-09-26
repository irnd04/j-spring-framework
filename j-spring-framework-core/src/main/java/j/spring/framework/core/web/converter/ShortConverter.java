package j.spring.framework.core.web.converter;

public class ShortConverter implements Converter<Short> {

    @Override
    public Short convert(String s) {
        if (s == null) {
            return null;
        }
        return Short.parseShort(s);
    }
}
