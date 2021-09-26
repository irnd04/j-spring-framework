package j.spring.framework.core.web.converter;

public class DoubleConverter implements Converter<Double> {
    @Override
    public Double convert(String s) {
        if (s == null) {
            return null;
        }
        return Double.parseDouble(s);
    }
}
