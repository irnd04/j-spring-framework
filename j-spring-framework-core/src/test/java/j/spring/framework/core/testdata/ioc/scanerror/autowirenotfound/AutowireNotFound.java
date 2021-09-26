package j.spring.framework.core.testdata.ioc.scanerror.autowirenotfound;

import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Controller;

@Controller
public class AutowireNotFound {

    @Autowired
    public AutowireNotFound(String str) {

    }

}
