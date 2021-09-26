package j.shop.app.exception;

public class ShopException extends RuntimeException {

    private final ErrorCode errorCode;

    private ShopException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    private ShopException(ShopErrorCode errorCode, Throwable e) {
        this.errorCode = errorCode;
        initCause(e);
    }

    public static ShopException of(ShopErrorCode errorCode, String... args) {
        return new ShopException(new ErrorCodeWithMessage(errorCode, args));
    }

    public static ShopException of(ShopErrorCode errorCode, Throwable e) {
        return new ShopException(errorCode, e);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}
