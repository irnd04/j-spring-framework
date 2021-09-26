package j.spring.framework.core.testdata.ioc.scanerror.cycle;

import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Component;

@Component
public class CycleConsInjectionComponent3 {

    private final CycleConsInjectionComponent2 cycleConsInjectionComponent2;

    @Autowired
    public CycleConsInjectionComponent3(CycleConsInjectionComponent2 cycleConsInjectionComponent2) {
        this.cycleConsInjectionComponent2 = cycleConsInjectionComponent2;
    }

    public CycleConsInjectionComponent2 getCycleConsInjectionComponent2() {
        return cycleConsInjectionComponent2;
    }
}
