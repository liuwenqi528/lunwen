package com.khcm.user.common.exception;

/**
 * Created by yangwb on 2017/11/23.
 */
public class ServiceException extends SystemException {

    public ServiceException(String errorInfo) {
        super(errorInfo);
    }

    public ServiceException(String errorInfo, Throwable throwable) {
        super(errorInfo, throwable);
    }

}
