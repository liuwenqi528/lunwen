package com.khcm.user.common.exception;

/**
 * Created by yangwb on 2017/11/22.
 */
public class DaoException extends SystemException {

    public DaoException(String errorInfo) {
        super(errorInfo);
    }

    public DaoException(String errorInfo, Throwable throwable) {
        super(errorInfo, throwable);
    }
}
