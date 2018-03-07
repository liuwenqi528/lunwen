package com.khcm.user.common.exception;

import com.khcm.user.common.errorcode.PredefinedRetCode;
import com.khcm.user.common.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yangwb on 2017/11/22.
 */
public class SecurityException extends AbstractException {

    public static final String ERROR_KEY = "global.error.security";

    public SecurityException(String errorInfo) {
        super(PredefinedRetCode.SECURITY_ERROR.getRetCode(), StringUtils.isBlank(errorInfo) ? SpringUtils.getMessage(ERROR_KEY) : errorInfo);
    }

    public SecurityException(String errorInfo, Throwable throwable) {
        super(PredefinedRetCode.SECURITY_ERROR.getRetCode(), StringUtils.isBlank(errorInfo) ? SpringUtils.getMessage(ERROR_KEY) : errorInfo, throwable);
    }

}
