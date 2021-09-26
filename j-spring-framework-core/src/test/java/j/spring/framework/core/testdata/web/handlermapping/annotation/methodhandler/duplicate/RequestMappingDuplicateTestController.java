package j.spring.framework.core.testdata.web.handlermapping.annotation.methodhandler.duplicate;

import j.spring.framework.core.web.annotation.Controller;
import j.spring.framework.core.web.annotation.RequestMapping;
import j.spring.framework.core.web.annotation.RequestMethod;

@Controller
public class RequestMappingDuplicateTestController {

    @RequestMapping(value = "/gg/{var1}", method = RequestMethod.GET)
    public void dup1() {

    }

    @RequestMapping(value = "/gg/{var1}", method = RequestMethod.POST)
    public void dup2() {

    }

    @RequestMapping(value = "/gg/{var1}")
    public void dup3() {

    }

}
