package j.spring.framework.core.testdata.ioc.ioc.service;

import j.spring.framework.core.testdata.ioc.ioc.component.SetterInjectionComponent;
import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Service;

@Service
public class SetterInjectionService {

    private SetterInjectionComponent setterInjectionComponent;

    @Autowired
    public void setSetterInjectionComponent(SetterInjectionComponent setterInjectionComponent) {
        this.setterInjectionComponent = setterInjectionComponent;
    }

    public SetterInjectionComponent getSetterInjectionComponent() {
        return setterInjectionComponent;
    }
}
