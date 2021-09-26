package j.spring.framework.core.exception;

public enum IocContainerErrorCode implements ErrorCode {

    IOC_CONTAINER_EXCEPTION(1000, ""),
    IOC_CONTAINER_NOT_FOUND(1001, "{} not found in IocContainer."),
    IOC_CONTAINER_NOT_FOUND_NO_ARG_CONSTRUCTOR(1002, "{} no arg constructor not found."),
    IOC_CONTAINER_AUTOWIRE_ONLY_ONE_CONSTRUCTOR_CAN_BE_USED(1003, "{} autowire only one constructor can be used."),
    IOC_CONTAINER_DETECTING_INJECTION_CYCLE(1005, "{} injection cycle has been detected."),
    IOC_CONTAINER_CANNOT_CHOOSE_INJECTION_OBJECT(1007, "{} cannot choose injection object"),
    IOC_CONTAINER_INTERFACES_CANNOT_BE_CREATED(1008, "{} interfaces cannot be created."),
    IOC_CONTAINER_NUMBER_OF_RESULT_ZERO_OR_ONE(1009, "number of results must be zero or one. class: {}, size: {}")
    ;


    IocContainerErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
