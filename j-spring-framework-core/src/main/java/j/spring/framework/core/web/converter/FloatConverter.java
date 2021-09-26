package j.spring.framework.core.web.converter;

public class FloatConverter implements Converter<Float> {
    @Override
    public Float convert(String s) {
        if (s == null) {
            return null;
        }
        return Float.parseFloat(s);
    }
}
