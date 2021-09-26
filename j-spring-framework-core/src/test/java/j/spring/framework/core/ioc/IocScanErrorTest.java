package j.spring.framework.core.ioc;

import j.spring.framework.core.exception.IocContainerErrorCode;
import j.spring.framework.core.exception.IocContainerException;
import j.spring.framework.core.testdata.ioc.scanerror.autowire2.Autowire2Component;
import j.spring.framework.core.testdata.ioc.scanerror.autowirenotfound.AutowireNotFound;
import j.spring.framework.core.testdata.ioc.scanerror.beancycle.Config;
import j.spring.framework.core.testdata.ioc.scanerror.cycle.CycleConsInjectionComponent1;
import j.spring.framework.core.testdata.ioc.scanerror.interfaceinjection.ServiceComponent;
import j.spring.framework.core.testdata.ioc.scanerror.noargsconstnotfound.NoArgsConstNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IocScanErrorTest {

    @Test
    @DisplayName("생성자 주입시 싸이클이 감지되면 오류 발생")
    void cycleConsInjectionErrorTest() {
        IocContainerException iocContainerException = assertThrows(IocContainerException.class, () -> {
            ComponentScanner scanner = new ComponentScanner(CycleConsInjectionComponent1.class.getPackage().getName());
            ComponentFactory factory = new ComponentFactory(scanner.scan());
        });

        assertSame(IocContainerErrorCode.IOC_CONTAINER_DETECTING_INJECTION_CYCLE, iocContainerException.getErrorCode());
    }

    @Test
    @DisplayName("두개이상의 생성자에 autowire를 사용하면 오류 발생")
    void constInjectionTest1() {
        IocContainerException iocContainerException = assertThrows(IocContainerException.class, () -> {
            ComponentScanner scanner = new ComponentScanner(Autowire2Component.class.getPackage().getName());
            ComponentFactory factory = new ComponentFactory(scanner.scan());
        });
        assertSame(IocContainerErrorCode.IOC_CONTAINER_AUTOWIRE_ONLY_ONE_CONSTRUCTOR_CAN_BE_USED, iocContainerException.getErrorCode());
    }

    @Test
    @DisplayName("생성자 주입시 모든 argument는 IocContainer가 관리 가능해야한다.")
    void constInjectionTest2() {
        IocContainerException iocContainerException = assertThrows(IocContainerException.class, () -> {
            ComponentScanner scanner = new ComponentScanner(AutowireNotFound.class.getPackage().getName());
            ComponentFactory factory = new ComponentFactory(scanner.scan());
        });
        assertSame(IocContainerErrorCode.IOC_CONTAINER_NOT_FOUND, iocContainerException.getErrorCode());
    }

    @Test
    @DisplayName("생성자 주입을 사용하지 않고 IocContainer가 관리하는 객체일 경우 반드시 no arg 생성자가 존재해야한다.")
    void constInjectionTest3() {
        IocContainerException iocContainerException = assertThrows(IocContainerException.class, () -> {
            ComponentScanner scanner = new ComponentScanner(NoArgsConstNotFound.class.getPackage().getName());
            ComponentFactory factory = new ComponentFactory(scanner.scan());
        });

        assertSame(IocContainerErrorCode.IOC_CONTAINER_NOT_FOUND_NO_ARG_CONSTRUCTOR, iocContainerException.getErrorCode());
    }

    @Test
    @DisplayName("주입시 해당 타입이 두개이면 오류 발생한다.")
    void interfaceTypeCannotChooseTest() {
        IocContainerException iocContainerException = assertThrows(IocContainerException.class, () -> {
            ComponentScanner scanner = new ComponentScanner(ServiceComponent.class.getPackage().getName());
            ComponentFactory factory = new ComponentFactory(scanner.scan());
        });
        assertSame(IocContainerErrorCode.IOC_CONTAINER_CANNOT_CHOOSE_INJECTION_OBJECT, iocContainerException.getErrorCode());
    }

    @Test
    @DisplayName("빈 싸이클이 감지되면 오류가 발생한다.")
    void beanCycleTest() {
        IocContainerException iocContainerException = assertThrows(IocContainerException.class, () -> {
            ComponentScanner scanner = new ComponentScanner(Config.class.getPackage().getName());
            ComponentFactory factory = new ComponentFactory(scanner.scan());
        });
        assertSame(IocContainerErrorCode.IOC_CONTAINER_DETECTING_INJECTION_CYCLE, iocContainerException.getErrorCode());
    }

}
