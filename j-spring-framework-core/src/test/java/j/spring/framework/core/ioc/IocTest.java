package j.spring.framework.core.ioc;

import j.spring.framework.core.testdata.ioc.ioc.bean.MyConfig;
import j.spring.framework.core.testdata.ioc.ioc.component.ConsInjectionComponent;
import j.spring.framework.core.testdata.ioc.ioc.component.FieldInjectionComponent;
import j.spring.framework.core.testdata.ioc.ioc.component.SetterInjectionComponent;
import j.spring.framework.core.testdata.ioc.ioc.controller.*;
import j.spring.framework.core.testdata.ioc.ioc.interfaceinjection.*;
import j.spring.framework.core.testdata.ioc.ioc.service.ConsInjectionService;
import j.spring.framework.core.testdata.ioc.ioc.service.FieldInjectionService;
import j.spring.framework.core.testdata.ioc.ioc.service.SetterInjectionService;
import j.spring.framework.core.web.config.StaticResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class IocTest {

    private ComponentFactory factory;

    @BeforeEach
    void beforeEach() {
        ComponentScanner scanner = new ComponentScanner("j.spring.framework.core.testdata.ioc.ioc");
        factory = new ComponentFactory(scanner.scan());
    }

    @Test
    @DisplayName("필드 주입 테스트")
    void fieldInjectionTest() {
        FieldInjectionController fieldInjectionController = factory.get(FieldInjectionController.class);
        FieldInjectionService fieldInjectionService = factory.get(FieldInjectionService.class);
        FieldInjectionComponent fieldInjectionComponent = factory.get(FieldInjectionComponent.class);
        assertNotNull(fieldInjectionController);
        assertNotNull(fieldInjectionController.getFieldInjectionService());
        assertNotNull(fieldInjectionController.getFieldInjectionService().getFieldInjectionComponent());
        assertSame(fieldInjectionService, fieldInjectionController.getFieldInjectionService());
        assertSame(fieldInjectionComponent, fieldInjectionController.getFieldInjectionService().getFieldInjectionComponent());
    }

    @Test
    @DisplayName("사이클 필드 주입 테스트")
    void cycleTest() {
        CycleController1 c1 = factory.get(CycleController1.class);
        CycleController2 c2 = factory.get(CycleController2.class);

        assertNotNull(c1);
        assertNotNull(c1.getCycleController2());
        assertSame(c2, c1.getCycleController2());

        assertNotNull(c2);
        assertNotNull(c2.getCycleController1());
        assertSame(c1, c2.getCycleController1());
    }

    @Test
    @DisplayName("세터 주입 테스트")
    void setterInjectionTest() {
        SetterInjectionController setterInjectionController = factory.get(SetterInjectionController.class);
        SetterInjectionService setterInjectionService = factory.get(SetterInjectionService.class);
        SetterInjectionComponent setterInjectionComponent = factory.get(SetterInjectionComponent.class);
        assertNotNull(setterInjectionController);
        assertNotNull(setterInjectionController.getSetterInjectionService());
        assertNotNull(setterInjectionController.getSetterInjectionService().getSetterInjectionComponent());
        assertSame(setterInjectionService, setterInjectionController.getSetterInjectionService());
        assertSame(setterInjectionComponent, setterInjectionController.getSetterInjectionService().getSetterInjectionComponent());
    }

    @Test
    @DisplayName("생성자 주입 테스트")
    void consInjectionTest() {
        ConsInjectionController consInjectionController = factory.get(ConsInjectionController.class);
        ConsInjectionService consInjectionService = factory.get(ConsInjectionService.class);
        ConsInjectionComponent consInjectionComponent = factory.get(ConsInjectionComponent.class);
        assertNotNull(consInjectionController);
        assertNotNull(consInjectionController.getConsInjectionService());
        assertNotNull(consInjectionController.getConsInjectionService().getConsInjectionComponent());
        assertSame(consInjectionService, consInjectionController.getConsInjectionService());
        assertSame(consInjectionComponent, consInjectionController.getConsInjectionService().getConsInjectionComponent());
    }

    @Test
    @DisplayName("interface로 생성이 가능하다.")
    void interfaceTypeTest() {
        InterfaceTypeService interfaceTypeService = factory.get(InterfaceTypeService.class);
        InterfaceType interfaceType = factory.get(InterfaceType.class);
        InterfaceType2Impl interfaceType2 = (InterfaceType2Impl) factory.get(InterfaceType2.class);
        ServiceComponent serviceComponent = factory.get(ServiceComponent.class);
        assertNotNull(interfaceTypeService);
        assertNotNull(interfaceTypeService.getSuperType());
        assertNotNull(interfaceTypeService.getSuperType2());
        assertSame(interfaceTypeService.getSuperType(), interfaceType);
        assertSame(interfaceTypeService.getSuperType2(), interfaceType2);
        assertSame(interfaceType2.getServiceComponent(), serviceComponent);
    }

    @Test
    @DisplayName("Bean Test")
    void beanTest() {
        ComponentScanner scanner = new ComponentScanner(MyConfig.class.getPackage().getName());
        ComponentFactory factory = new ComponentFactory(scanner.scan());
        StaticResourceConfig config = factory.get(StaticResourceConfig.class);
        assertNotNull(config);
        assertIterableEquals(Collections.singletonList("/*"), config.getResourceMappingUrlPatterns());
        assertEquals("2", config.getResourcePrefix());
    }

}