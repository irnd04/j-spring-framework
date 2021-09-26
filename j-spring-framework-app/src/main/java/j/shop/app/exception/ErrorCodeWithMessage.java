package j.shop.app.exception;

import org.slf4j.helpers.MessageFormatter;

public class ErrorCodeWithMessage implements ErrorCode {

    private final ErrorCode errorCode;
    private final String[] args;

    public ErrorCodeWithMessage(ErrorCode errorCode, String... args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    @Override
    public int getCode() {
        return errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return MessageFormatter.arrayFormat(errorCode.getMessage(), args).getMessage();
    }
}
