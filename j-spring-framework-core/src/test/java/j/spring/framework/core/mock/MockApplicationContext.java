package j.spring.framework.core.mock;

import j.spring.framework.core.ioc.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MockApplicationContext implements ApplicationContext {
    @Override
    public <T> T get(Class<T> clazz) {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return Collections.emptyList();
    }

    @Override
    public <T> List<T> find(Class<T> clazz) {
        return new ArrayList<>();
    }

    @Override
    public <T> T findOne(Class<T> clazz) {
        return null;
    }
}
