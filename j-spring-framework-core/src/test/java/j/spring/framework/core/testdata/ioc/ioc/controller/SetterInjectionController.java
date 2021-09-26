package j.spring.framework.core.testdata.ioc.ioc.controller;

import j.spring.framework.core.testdata.ioc.ioc.service.SetterInjectionService;
import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Controller;

@Controller
public class SetterInjectionController {

    private SetterInjectionService setterInjectionService;

    @Autowired
    public void setSetterInjectionService(SetterInjectionService setterInjectionService) {
        this.setterInjectionService = setterInjectionService;
    }

    public SetterInjectionService getSetterInjectionService() {
        return setterInjectionService;
    }
}
