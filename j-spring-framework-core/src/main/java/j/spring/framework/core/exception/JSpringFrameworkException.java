package j.spring.framework.core.exception;

import j.spring.framework.core.web.util.StringUtils;
import org.slf4j.helpers.MessageFormatter;

public class JSpringFrameworkException extends RuntimeException {

    protected final ErrorCode errorCode;
    protected final String[] args;

    public JSpringFrameworkException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.args = new String[0];
    }

    public JSpringFrameworkException(ErrorCode errorCode, Throwable e) {
        this.errorCode = errorCode;
        this.args = new String[0];
        initCause(e);
    }

    public JSpringFrameworkException(ErrorCode errorCode, String... args) {
        this.errorCode = errorCode;
        this.args = args;
    }


    public ErrorCode getErrorCode() {
        return errorCode;
    }

    private String getErrorCodeMessage() {
        return MessageFormatter.arrayFormat(errorCode.getMessage(), args).getMessage();
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append('[')
            .append(errorCode.name())
            .append(']');

        if (!StringUtils.isBlank(getErrorCodeMessage())) {
            sb.append(" ");
            sb.append(getErrorCodeMessage());
        }
        return sb.toString();
    }
}
