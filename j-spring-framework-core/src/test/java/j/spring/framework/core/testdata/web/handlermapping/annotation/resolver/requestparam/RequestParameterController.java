package j.spring.framework.core.testdata.web.handlermapping.annotation.resolver.requestparam;

import j.spring.framework.core.web.annotation.Controller;
import j.spring.framework.core.web.annotation.RequestMapping;

@Controller
@RequestMapping("/param")
public class RequestParameterController {

    @RequestMapping("/var1int1")
    public void var1int1(String var1, int int1) {

    }

    @RequestMapping("/var1var2")
    public void var1var2(String var1, String var2) {

    }

    @RequestMapping("/int1int2")
    public void int1int2(int[] int1, int[] int2) {

    }
}
