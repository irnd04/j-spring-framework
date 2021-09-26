package j.spring.framework.core.testdata.ioc.ioc.interfaceinjection;

import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Service;

@Service
public class InterfaceType2Impl implements InterfaceType2 {

    private final ServiceComponent serviceComponent;

    @Autowired
    public InterfaceType2Impl(ServiceComponent serviceComponent) {
        this.serviceComponent = serviceComponent;
    }

    public ServiceComponent getServiceComponent() {
        return serviceComponent;
    }
}
