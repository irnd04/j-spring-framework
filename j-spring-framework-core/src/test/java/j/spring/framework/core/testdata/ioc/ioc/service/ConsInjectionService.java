package j.spring.framework.core.testdata.ioc.ioc.service;

import j.spring.framework.core.testdata.ioc.ioc.component.ConsInjectionComponent;
import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Service;

@Service
public class ConsInjectionService {

    private final ConsInjectionComponent consInjectionComponent;

    @Autowired
    public ConsInjectionService(ConsInjectionComponent consInjectionComponent) {
        this.consInjectionComponent = consInjectionComponent;
    }

    public ConsInjectionComponent getConsInjectionComponent() {
        return consInjectionComponent;
    }
}
