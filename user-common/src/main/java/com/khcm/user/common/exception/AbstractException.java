package com.khcm.user.common.exception;

/**
 * 系统异常
 * @author wangtao
 * @date 5/10/17
 */
public abstract class AbstractException extends RuntimeException {

    private static final String MESSAGE = "errorCode=%s,errorInfo=%s";

    private String errorCode;
    private String errorInfo;

    public AbstractException(String errorCode, String errorInfo) {
        super(String.format(MESSAGE, errorCode, errorInfo));
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    public AbstractException(String errorCode, String errorInfo, Throwable throwable) {
        super(String.format(MESSAGE, errorCode, errorInfo), throwable);
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

}
