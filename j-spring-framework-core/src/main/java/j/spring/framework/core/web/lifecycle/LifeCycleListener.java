package j.spring.framework.core.web.lifecycle;

import org.apache.catalina.Lifecycle;

@FunctionalInterface
public interface LifeCycleListener {
    void listen(LifeCycle lifeCycle);
}
