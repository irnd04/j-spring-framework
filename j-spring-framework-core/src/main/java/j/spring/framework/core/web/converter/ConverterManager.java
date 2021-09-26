package j.spring.framework.core.web.converter;

import com.google.common.collect.ImmutableMap;
import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;
import j.spring.framework.core.ioc.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConverterManager {

    private static final Map<Class<?>, Converter<?>> converterMap = new ImmutableMap.Builder<Class<?>, Converter<?>>()
            .put(Long.class, new LongConverter())
            .put(Integer.class, new IntegerConverter())
            .put(String.class, new StringConverter())
            .put(Double.class, new DoubleConverter())
            .put(Boolean.class, new BooleanConverter())
            .build();

    private final Map<Class<?>, Converter<?>> customConverterMap;


    public ConverterManager(ApplicationContext applicationContext) {

        Map<Class<?>, Converter<?>> customConverterMap = new HashMap<>();

        @SuppressWarnings("rawtypes")
        List customConverters = applicationContext.find(Converter.class);
        for (Object o : customConverters) {
            Converter<?> converter = (Converter<?>) o;
            ParameterizedType parameterizedType = (ParameterizedType) converter.getClass().getGenericInterfaces()[0];
            String typeName = parameterizedType.getActualTypeArguments()[0].getTypeName();
            try {
                Class<?> clazz = Class.forName(typeName);
                customConverterMap.put(clazz, converter);
            } catch (ClassNotFoundException e) {
                throw WebServerException.of(WebServerErrorCode.CONVERTER_EXCEPTION, e);
            }
        }

        this.customConverterMap = ImmutableMap.copyOf(customConverterMap);
    }

    public Object convert(String s, Class<?> clazz) {
        Class<?> wrapClass = PrimitiveTypeUtils.wrap(clazz);

        Converter<?> converter = customConverterMap.get(wrapClass);
        if (converter == null) {
            converter = converterMap.get(wrapClass);
        }

        if (converter == null) {
            throw WebServerException
                    .of(WebServerErrorCode.CONVERTER_NOT_FOUND, String.class.getName(), wrapClass.getName());
        }

        Object result = converter.convert(s);

        if (result == null) {
            return PrimitiveTypeUtils.defaultValue(clazz);
        }

        return result;
    }

}
