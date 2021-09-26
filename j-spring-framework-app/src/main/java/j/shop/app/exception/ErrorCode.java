package j.shop.app.exception;

public interface ErrorCode {

    int getCode();
    String getMessage();

    static boolean equals(ErrorCode code1, ErrorCode code2) {
        return code1.getCode() == code2.getCode();
    }

}
