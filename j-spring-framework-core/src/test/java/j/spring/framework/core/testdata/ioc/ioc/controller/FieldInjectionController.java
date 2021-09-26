package j.spring.framework.core.testdata.ioc.ioc.controller;

import j.spring.framework.core.testdata.ioc.ioc.service.FieldInjectionService;
import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Controller;

@Controller
public class FieldInjectionController {

    @Autowired
    private FieldInjectionService fieldInjectionService;

    public FieldInjectionService getFieldInjectionService() {
        return fieldInjectionService;
    }
}
