package j.spring.framework.core.testdata.ioc.scanerror.interfaceinjection;

import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Component;

@Component
public class ServiceComponent {

    @Autowired
    private IService iService;

}
