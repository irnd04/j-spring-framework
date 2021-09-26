package j.spring.framework.core.web.converter;

public class StringConverter implements Converter<String> {

    @Override
    public String convert(String s) {
        return s;
    }
}
