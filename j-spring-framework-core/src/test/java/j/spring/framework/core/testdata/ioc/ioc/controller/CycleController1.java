package j.spring.framework.core.testdata.ioc.ioc.controller;

import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Controller;

@Controller
public class CycleController1 {
    @Autowired
    CycleController2 cycleController2;

    public CycleController2 getCycleController2() {
        return cycleController2;
    }
}
