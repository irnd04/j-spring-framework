package j.spring.framework.core.ioc;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class InjectorManager {

    private static final List<Injector> injectors
            = ImmutableList.of(new FieldInjector(), new SetterInjector());

    public static void inject(Object o, ComponentFactory factory) {
        for (Injector injector : injectors) {
            injector.inject(o, factory);
        }
    }

}
