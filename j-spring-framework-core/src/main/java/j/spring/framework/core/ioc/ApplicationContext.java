package j.spring.framework.core.ioc;

import java.util.Collection;
import java.util.List;

public interface ApplicationContext {
    <T> T get(Class<T> clazz);
    Collection<Object> values();
    <T> List<T> find(Class<T> clazz);
    <T> T findOne(Class<T> clazz);
}
