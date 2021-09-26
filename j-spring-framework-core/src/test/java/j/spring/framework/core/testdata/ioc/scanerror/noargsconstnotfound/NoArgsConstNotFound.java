package j.spring.framework.core.testdata.ioc.scanerror.noargsconstnotfound;

import j.spring.framework.core.web.annotation.Controller;

@Controller
public class NoArgsConstNotFound {

    public NoArgsConstNotFound(String str) { }

}
