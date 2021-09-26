package j.spring.framework.core.testdata.ioc.ioc.interfaceinjection;

import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Service;

@Service
public class InterfaceTypeService {

    private final InterfaceType interfaceType;
    private final InterfaceType2 interfaceType2;

    @Autowired
    public InterfaceTypeService(InterfaceType interfaceType, InterfaceType2 interfaceType2) {
        this.interfaceType = interfaceType;
        this.interfaceType2 = interfaceType2;
    }

    public InterfaceType getSuperType() {
        return interfaceType;
    }

    public InterfaceType2 getSuperType2() {
        return interfaceType2;
    }
}
