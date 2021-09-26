package j.shop.app.web;

import j.shop.app.exception.ErrorCode;

public class ShopResponse<T> {
    private ShopHeader header;
    private T result;

    public static <R> ShopResponse<R> success(R result) {
        ShopResponse<R> response = new ShopResponse<>();
        response.setHeader(ShopHeader.ok());
        response.setResult(result);
        return response;
    }

    public static ShopResponse<?> failure(ErrorCode errorCode) {
        ShopResponse<?> response = new ShopResponse<>();
        response.setHeader(ShopHeader.errorCodeOf(errorCode));
        response.setResult(null);
        return response;
    }

    public ShopHeader getHeader() {
        return header;
    }

    public T getResult() {
        return result;
    }

    public void setHeader(ShopHeader header) {
        this.header = header;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
