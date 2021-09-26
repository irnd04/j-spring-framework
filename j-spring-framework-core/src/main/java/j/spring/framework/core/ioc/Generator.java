package j.spring.framework.core.ioc;

public interface Generator {
    Class<?>[] getArgTypes();
    Object create(Object... args);
    Class<?> getType();
}
