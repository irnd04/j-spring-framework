package j.spring.framework.core.testdata.web.interceptor;

import j.spring.framework.core.web.annotation.Controller;
import j.spring.framework.core.web.annotation.RequestMapping;
import j.spring.framework.core.web.annotation.ResponseBody;

@Controller
@ResponseBody
public class InterceptorController {

    @RequestMapping("/handle")
    public void handle() {

    }

}
