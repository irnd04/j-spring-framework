package j.spring.framework.core.exception;

public class IocContainerException extends JSpringFrameworkException {

    private IocContainerException(IocContainerErrorCode errorCode) {
        super(errorCode);
    }

    private IocContainerException(IocContainerErrorCode errorCode, Throwable e) {
        super(errorCode, e);
    }

    public IocContainerException(ErrorCode errorCode, String... args) {
        super(errorCode, args);
    }

    public static IocContainerException of(IocContainerErrorCode errorCode, String... args) {
        return new IocContainerException(errorCode, args);
    }

    public static IocContainerException of(IocContainerErrorCode errorCode, Throwable e) {
        return new IocContainerException(errorCode, e);
    }
}
