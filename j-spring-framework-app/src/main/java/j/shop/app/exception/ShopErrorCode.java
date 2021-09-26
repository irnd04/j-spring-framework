package j.shop.app.exception;

public enum ShopErrorCode implements ErrorCode {

    NOT_FOUND(404, "{} 해당 페이지를 찾지 못했습니다."),
    SERVER_ERROR(500, "일시적으로 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."),

    SHOP_NOT_FOUND(101, "ID({})를 가진 가게를 찾지 못했습니다."),
    SHOP_REQUEST_DATA_INVALID(102, "가게명과 주소는 모두 존재해야 합니다."),
    SHOP_ID_MUST_BE_NUMBER(103, "가게의 ID는 숫자여야 합니다."),

    ;

    ShopErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }
}
