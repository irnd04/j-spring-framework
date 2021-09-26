package j.spring.framework.core.exception;

public enum WebServerErrorCode implements ErrorCode {
    // converter
    CONVERTER_EXCEPTION(100, ""),
    CONVERTER_NOT_FOUND(101, "{} --> {} Converter not found."),

    // handler mapping
    METHOD_MUST_HAVE_REQUEST_MAPPING_ANNOTATION(201, "{} method must have RequestMapping Annotation"),
    PATH_VARIABLE_NOT_FOUND(202, "{} PathVariable not found."),
    HTTP_BODY_NOT_READABLE(203, "http body not readable."),
    AMBIGUOUS_REQUEST_MAPPING(204, "{} {} {} ambiguous request mapping"),
    DUPLICATE_REQUEST_MAPPING(205, "{}, {} duplicate request mapping."),

    // unsupported
    FRAMEWORK_UNSUPPORTED(801, "{} unsupported."),

    // servlet
    NOT_FOUND_HANDLER_ADAPTER(901, "not found handler adapter."),
    NOT_FOUND_EXCEPTION_HANDLER(902, "not found exception handler."),

    ;

    WebServerErrorCode(int code, String message) {
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
        return this.code;
    }

}
