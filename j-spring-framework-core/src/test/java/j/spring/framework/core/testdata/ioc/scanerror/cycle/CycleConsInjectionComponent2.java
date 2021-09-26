package j.spring.framework.core.testdata.ioc.scanerror.cycle;

import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Component;

@Component
public class CycleConsInjectionComponent2 {

    private final CycleConsInjectionComponent3 cycleConsInjectionComponent3;

    @Autowired
    public CycleConsInjectionComponent2(CycleConsInjectionComponent3 cycleConsInjectionComponent3) {
        this.cycleConsInjectionComponent3 = cycleConsInjectionComponent3;
    }

    public CycleConsInjectionComponent3 getCycleConsInjectionComponent3() {
        return cycleConsInjectionComponent3;
    }
}
