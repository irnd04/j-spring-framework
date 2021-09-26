package j.spring.framework.core.testdata.ioc.ioc.controller;

import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Controller;

@Controller
public class CycleController2 {
    @Autowired
    CycleController1 cycleController1;

    public CycleController1 getCycleController1() {
        return cycleController1;
    }
}
