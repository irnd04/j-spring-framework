package j.spring.framework.core.testdata.ioc.ioc.controller;

import j.spring.framework.core.testdata.ioc.ioc.service.ConsInjectionService;
import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Controller;

@Controller
public class ConsInjectionController {

    private final ConsInjectionService consInjectionService;

    @Autowired
    public ConsInjectionController(ConsInjectionService consInjectionService) {
        this.consInjectionService = consInjectionService;
    }

    public ConsInjectionService getConsInjectionService() {
        return consInjectionService;
    }
}
