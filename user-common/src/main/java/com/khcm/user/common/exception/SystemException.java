package com.khcm.user.common.exception;

import com.khcm.user.common.errorcode.PredefinedRetCode;
import com.khcm.user.common.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yangwb on 2017/11/22.
 */
public class SystemException extends AbstractException {

    public static final String ERROR_KEY = "global.error.system";

    public SystemException(String errorInfo) {
        super(PredefinedRetCode.SYSTEM_ERROR.getRetCode(), StringUtils.isBlank(errorInfo) ? SpringUtils.getMessage(ERROR_KEY) : errorInfo);
    }

    public SystemException(String errorInfo, Throwable throwable) {
        super(PredefinedRetCode.SYSTEM_ERROR.getRetCode(), StringUtils.isBlank(errorInfo) ? SpringUtils.getMessage(ERROR_KEY) : errorInfo, throwable);
    }

    public SystemException(Throwable throwable) {
        this(null, throwable);
    }

}
