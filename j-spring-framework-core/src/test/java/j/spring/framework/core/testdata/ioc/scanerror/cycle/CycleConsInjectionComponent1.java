package j.spring.framework.core.testdata.ioc.scanerror.cycle;

import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Component;

@Component
public class CycleConsInjectionComponent1 {

    private final CycleConsInjectionComponent2 cycleConsInjectionComponent2;
    private final CycleConsInjectionComponent3 cycleConsInjectionComponent3;

    @Autowired
    public CycleConsInjectionComponent1(CycleConsInjectionComponent2 cycleConsInjectionComponent2, CycleConsInjectionComponent3 cycleConsInjectionComponent3) {
        this.cycleConsInjectionComponent2 = cycleConsInjectionComponent2;
        this.cycleConsInjectionComponent3 = cycleConsInjectionComponent3;
    }

    public CycleConsInjectionComponent2 getCycleConsInjectionComponent2() {
        return cycleConsInjectionComponent2;
    }

    public CycleConsInjectionComponent3 getCycleConsInjectionComponent3() {
        return cycleConsInjectionComponent3;
    }
}
