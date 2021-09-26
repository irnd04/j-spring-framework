package j.spring.framework.core.web.converter;

import com.google.common.collect.ImmutableMap;

public class PrimitiveTypeUtils {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> wrap(Class<T> c) {
        return c.isPrimitive() ? (Class<T>) PRIMITIVES_TO_WRAPPERS.get(c) : c;
    }

    public static <T> Object defaultValue(Class<T> c) {
        return PRIMITIVES_DEFAULT_VALUE.get(c);
    }

    private static final ImmutableMap<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS =
            ImmutableMap.<Class<?>, Class<?>>builder()
                    .put(boolean.class, Boolean.class)
                    .put(char.class, Character.class)
                    .put(double.class, Double.class)
                    .put(float.class, Float.class)
                    .put(int.class, Integer.class)
                    .put(long.class, Long.class)
                    .put(short.class, Short.class)
                    .build();

    private static final ImmutableMap<Class<?>, Object> PRIMITIVES_DEFAULT_VALUE =
            ImmutableMap.<Class<?>, Object>builder()
                    .put(boolean.class, false)
                    .put(char.class, '\u0000')
                    .put(double.class, 0.0d)
                    .put(float.class, 0f)
                    .put(int.class, 0)
                    .put(long.class, 0L)
                    .put(short.class, 0)
                    .build();

}
