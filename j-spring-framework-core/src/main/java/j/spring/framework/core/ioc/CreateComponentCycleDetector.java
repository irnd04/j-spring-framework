package j.spring.framework.core.ioc;

import com.google.common.collect.Sets;
import j.spring.framework.core.exception.IocContainerErrorCode;
import j.spring.framework.core.exception.IocContainerException;

import java.util.Set;

class CreateComponentCycleDetector {

    private final Set<Class<?>> visits;

    public CreateComponentCycleDetector() {
        visits = Sets.newHashSet();
    }

    void detect(Class<?> clazz) {
        if (visits.contains(clazz)) {
            throw IocContainerException
                    .of(IocContainerErrorCode.IOC_CONTAINER_DETECTING_INJECTION_CYCLE, clazz.getName());
        }
        visits.add(clazz);
    }

}
