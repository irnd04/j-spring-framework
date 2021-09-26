package j.spring.framework.core.testdata.web.handlermapping.annotation.methodhandler.controller;

import j.spring.framework.core.web.annotation.Controller;
import j.spring.framework.core.web.annotation.RequestMapping;
import j.spring.framework.core.web.annotation.ResponseBody;

@Controller
@RequestMapping("/test1/test2")
@ResponseBody
public class ControllerRequestMappingFinderTestController {

    @RequestMapping("/test3")
    public String controllerRequestMappingTest() {
        return "123";
    }

}
