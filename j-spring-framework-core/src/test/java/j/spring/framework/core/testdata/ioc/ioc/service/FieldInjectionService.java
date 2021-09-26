package j.spring.framework.core.testdata.ioc.ioc.service;

import j.spring.framework.core.testdata.ioc.ioc.component.FieldInjectionComponent;
import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Service;

@Service
public class FieldInjectionService {

    @Autowired
    private FieldInjectionComponent fieldInjectionComponent;

    public FieldInjectionComponent getFieldInjectionComponent() {
        return fieldInjectionComponent;
    }
}
