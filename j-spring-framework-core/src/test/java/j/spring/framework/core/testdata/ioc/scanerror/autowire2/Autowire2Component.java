package j.spring.framework.core.testdata.ioc.scanerror.autowire2;

import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Component;

@Component
public class Autowire2Component {

    @Autowired
    public Autowire2Component(String s, String z) {}

    @Autowired
    public Autowire2Component(String str) {}

}
