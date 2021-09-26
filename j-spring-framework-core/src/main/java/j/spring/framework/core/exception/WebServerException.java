package j.spring.framework.core.exception;

public class WebServerException extends JSpringFrameworkException {

    private WebServerException(WebServerErrorCode errorCode) {
        super(errorCode);
    }

    private WebServerException(WebServerErrorCode errorCode, Throwable e) {
        super(errorCode, e);
    }

    private WebServerException(ErrorCode errorCode, String... args) {
        super(errorCode, args);
    }

    public static WebServerException of(WebServerErrorCode errorCode, String... args) {
        return new WebServerException(errorCode, args);
    }

    public static WebServerException of(WebServerErrorCode errorCode, Throwable e) {
        return new WebServerException(errorCode, e);
    }

}
