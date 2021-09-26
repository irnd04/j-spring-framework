package j.shop.app.web;

import j.shop.app.exception.ErrorCode;

public class ShopHeader {

    public static ShopHeader errorCodeOf(ErrorCode errorCode) {
        ShopHeader shopHeader = new ShopHeader();
        shopHeader.setCode(errorCode.getCode());
        shopHeader.setMessage(errorCode.getMessage());
        shopHeader.setSuccess(false);
        return shopHeader;
    }

    public static ShopHeader ok() {
        ShopHeader shopHeader = new ShopHeader();
        shopHeader.setCode(200);
        shopHeader.setMessage("OK");
        shopHeader.setSuccess(true);
        return shopHeader;
    }

    private int code;
    private String message;
    private boolean isSuccess;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
