package j.spring.framework.core.web.server.lifecycle;

@FunctionalInterface
public interface LifeCycleListener {
    void listen(LifeCycle lifeCycle);
}
