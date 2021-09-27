package j.spring.framework.core.web.server.lifecycle;

import java.util.ArrayList;
import java.util.List;

public class LifeCycleEventBus {
    private static List<LifeCycleListener> listeners = new ArrayList<>();

    public static void put(LifeCycleListener lifeCycleListener) {
        listeners.add(lifeCycleListener);
    }

    public static void send(LifeCycle lifeCycle) {
        listeners.forEach(listener -> listener.listen(lifeCycle));
    }
}
